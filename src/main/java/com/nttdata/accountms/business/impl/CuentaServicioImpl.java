package com.nttdata.accountms.business.impl;


import com.nttdata.accountms.business.CuentaServicio;
import com.nttdata.accountms.business.excepcion.ErrorInternoExcepcion;
import com.nttdata.accountms.business.excepcion.RecursoNoEncontradoExcepcion;
import com.nttdata.accountms.business.mapper.CuentaMapper;
import com.nttdata.accountms.business.util.Constantes;
import com.nttdata.accountms.business.util.HttpConnection;
import com.nttdata.accountms.model.CuentaRequest;
import com.nttdata.accountms.model.CuentaResponse;
import com.nttdata.accountms.model.TransaccionRequest;
import com.nttdata.accountms.model.entity.Cuenta;
import com.nttdata.accountms.repository.CuentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CuentaServicioImpl implements CuentaServicio {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private CuentaMapper cuentaMapper;

    @Autowired
    private HttpConnection httpConnection;

    @Override
    public List<CuentaResponse> listarCuentas() {
        return cuentaRepositorio.findAll().stream()
                .map(m -> cuentaMapper.getCuentaListResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public CuentaResponse guardarCuenta(CuentaRequest request) {
        Cuenta cuenta = cuentaMapper.getCuentaEntity(request);
        try {
            Boolean validarClienteId = this.validarIdCliente(cuenta.getClienteId());
            if (!validarClienteId)
                throw new ErrorInternoExcepcion("Error en el saldo registrado");
            Boolean validarSaldo = Cuenta.validarSaldo(cuenta.getSaldo());
            if (!validarSaldo)
                throw new ErrorInternoExcepcion("Error en el saldo registrado");

            String numeroCuenta = Cuenta.getCuentaAleatoria();
            cuenta.setNumeroCuenta(numeroCuenta);
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return cuentaMapper
                .getCuentaListResponse(cuentaRepositorio.
                        save(cuenta));
    }

    @Override
    public CuentaResponse buscarCuentaPorId(Integer idCuenta) {
        Cuenta cuenta = this.cuentaRepositorio.findById(idCuenta).orElse(null);
        if(cuenta == null)
            throw new RecursoNoEncontradoExcepcion("No existe el id cuenta: "+ idCuenta);
        return cuentaMapper.getCuentaListResponse(cuenta);
    }

    @Override
    public void eliminarCuentaPorId(Integer idCuenta) {
        CuentaResponse response = this.buscarCuentaPorId(idCuenta);
        if(response == null)
            throw new RecursoNoEncontradoExcepcion("No existe el id cuenta: "+idCuenta);
        this.cuentaRepositorio.deleteById(idCuenta);
    }

    @Override
    public CuentaResponse depositar(Integer idCuenta, TransaccionRequest transaccionRequest) {
        CuentaResponse response = this.buscarCuentaPorId(idCuenta);
        Double saldo = response.getSaldo();

        try{
            saldo += transaccionRequest.getMonto();
            response.setSaldo(saldo);
            response = this.modificarSaldo(response);

        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }

        return response;
    }

    @Override
    public CuentaResponse retirar(Integer idCuenta, TransaccionRequest transaccionRequest) {
        CuentaResponse response = this.buscarCuentaPorId(idCuenta);
        try{
            Double saldo = response.getSaldo();
            String tipoCuenta = response.getTipoCuenta();

            saldo -= transaccionRequest.getMonto();

            if (!validarRetiro(saldo, tipoCuenta))
                throw new ErrorInternoExcepcion("Error en el saldo retirado");
            response.setSaldo(saldo);
            response = this.modificarSaldo(response);

        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }

        return response;
    }

    private boolean validarRetiro(Double saldo, String tipoCuenta){
        boolean validacion = true;
        if(saldo < Constantes.LIMITE_CUENTA_AHORROS && tipoCuenta.equals("AHORROS"))
            validacion = false;
        if(saldo < Constantes.LIMITE_CUENTA_CORRIENTE && tipoCuenta.equals("CORRIENTE"))
            validacion = false;
        return validacion;
    }

    private CuentaResponse modificarSaldo(CuentaResponse response){
        return cuentaMapper.getCuentaListResponse(cuentaRepositorio.
                save(cuentaMapper.getCuentaOfResponseForTransaction(response)));
    }

    private boolean validarIdCliente(Integer id) {
        boolean validacion = true;
        Map<String, Object> cliente = getClienteById(id);
        if(cliente==null)
            validacion = false;
        return validacion;
    }

    private Map<String, Object> getClienteById(Integer id)  {
        Map<String, Object> cliente = new HashMap<>();
        String uri = Constantes.URL_CLIENTEMS+id;

        cliente = httpConnection.stringToMap(httpConnection.ejecutarSolicitudHttp(uri, "GET").toString());

        return cliente;
    }

}

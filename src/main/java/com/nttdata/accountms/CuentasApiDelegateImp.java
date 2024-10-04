package com.nttdata.accountms;

import com.nttdata.accountms.api.CuentasApiDelegate;
import com.nttdata.accountms.business.CuentaServicio;
import com.nttdata.accountms.model.CuentaRequest;
import com.nttdata.accountms.model.CuentaResponse;
import com.nttdata.accountms.model.TransaccionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuentasApiDelegateImp implements CuentasApiDelegate {

    @Autowired
    private CuentaServicio cuentaServicio;


    @Override
    public ResponseEntity<List<CuentaResponse>> listCuenta(){
        return ResponseEntity.ok(cuentaServicio.listarCuentas());
    }

    @Override
    public ResponseEntity<CuentaResponse> agregarCuenta(CuentaRequest cuentaRequest){
        return ResponseEntity.ok(cuentaServicio.guardarCuenta(cuentaRequest));
    }

    @Override
    public ResponseEntity<CuentaResponse> consultarCuenta(Integer id){
        return ResponseEntity.ok(cuentaServicio.buscarCuentaPorId(id));
    }

    @Override
    public ResponseEntity<Void> eliminarCuentaPorId(Integer id){
        this.cuentaServicio.eliminarCuentaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CuentaResponse> depositar(Integer id,
                                                    TransaccionRequest transaccionRequest){
        return ResponseEntity.ok(cuentaServicio.depositar(id, transaccionRequest));
    }

    @Override
    public ResponseEntity<CuentaResponse> retirar(Integer id,
                                                  TransaccionRequest transaccionRequest){
        return ResponseEntity.ok(cuentaServicio.retirar(id, transaccionRequest));
    }

}

package com.nttdata.accountms.business.mapper;

import com.nttdata.accountms.model.CuentaRequest;
import com.nttdata.accountms.model.CuentaResponse;
import com.nttdata.accountms.model.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta getCuentaEntity(CuentaRequest request){
        Cuenta entity = new Cuenta();
        entity.setSaldo(request.getSaldo());
        entity.setTipoCuenta(Cuenta.TipoCuenta.valueOf(request.getTipoCuenta()));
        entity.setClienteId(request.getClienteId());
        return entity;
    }

    public CuentaResponse getCuentaListResponse(Cuenta cuenta){
        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setSaldo(cuenta.getSaldo());
        response.setTipoCuenta(cuenta.getTipoCuenta().toString());
        response.setClienteId(cuenta.getClienteId());
        return response;
    }

    public Cuenta getCuentaOfResponseForTransaction(CuentaResponse response){
        Cuenta entity = new Cuenta();
        entity.setId(response.getId());
        entity.setNumeroCuenta(response.getNumeroCuenta());
        entity.setSaldo(response.getSaldo());
        entity.setTipoCuenta(Cuenta.TipoCuenta.valueOf(response.getTipoCuenta()));
        entity.setClienteId(response.getClienteId());
        return entity;
    }

}

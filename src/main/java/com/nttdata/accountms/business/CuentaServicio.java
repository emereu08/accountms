package com.nttdata.accountms.business;

import com.nttdata.accountms.model.CuentaRequest;
import com.nttdata.accountms.model.CuentaResponse;
import com.nttdata.accountms.model.TransaccionRequest;

import java.util.List;

public interface CuentaServicio {
    List<CuentaResponse> listarCuentas();

    CuentaResponse guardarCuenta(CuentaRequest request) throws Exception;

    CuentaResponse buscarCuentaPorId(Integer idCuenta);

    void eliminarCuentaPorId(Integer idCuenta);

    CuentaResponse depositar(Integer idCuenta, TransaccionRequest transaccionRequest);

    CuentaResponse retirar(Integer idCuenta, TransaccionRequest transaccionRequest);
}

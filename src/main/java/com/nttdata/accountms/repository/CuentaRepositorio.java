package com.nttdata.accountms.repository;

import com.nttdata.accountms.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
}

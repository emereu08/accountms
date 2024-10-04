package com.nttdata.accountms.model.entity;

import com.nttdata.accountms.business.util.Constantes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false, unique = true)
    String numeroCuenta;
    @Column(nullable = false)
    Double saldo;
    @Column(nullable = false)
    TipoCuenta tipoCuenta;
    @Column(nullable = false)
    Integer clienteId;
    public enum TipoCuenta {
        AHORROS, CORRIENTE
    }

    public static String getCuentaAleatoria() {
        long cuentaAleatoria = 0L;

        cuentaAleatoria = Constantes.LIMITE_INFERIOR_NUMERO_CUENTA + (long) (Math.random() *
                (Constantes.LIMITE_SUPERIOR_NUMERO_CUENTA - Constantes.LIMITE_INFERIOR_NUMERO_CUENTA));

        return String.valueOf(cuentaAleatoria);
    }

    public static Boolean validarSaldo(Double saldo) {
        Boolean validacion = true;
        if(saldo<= 0)
            validacion = false;
        return validacion;
    }


}

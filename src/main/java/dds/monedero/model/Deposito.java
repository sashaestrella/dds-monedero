package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MontoNegativoException;

import java.time.LocalDate;

public class Deposito extends Movimiento {
    public Deposito(LocalDate fecha, double monto) {
        this.fecha = fecha;
        this.monto = monto;
    }

    @Override
    public void validarAplicacion(double saldoCuenta, long depositosDiarios, double limite) {
        if (monto <= 0) {
            throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
        }

        if (depositosDiarios >= 3) {
            throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
        }
    }

    public double montoAplicable() {
        return monto;
    }
}

package dds.monedero.model;

import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;

public class Extraccion extends Movimiento {
    public Extraccion(LocalDate fecha, double monto) {
        this.fecha = fecha;
        this.monto = monto;
    }

    @Override
    public void validarAplicacion(double saldoCuenta, long depositosDiarios, double limite) {
        super.validarAplicacion(saldoCuenta, depositosDiarios, limite);

        if (saldoCuenta - monto < 0) {
            throw new SaldoMenorException("No puede sacar mas de " + saldoCuenta + " $");
        }

        if (monto > limite) {
            throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000 + " diarios, límite: " + limite);
        }
    }

    public double montoAplicable() {
        return monto * -1;
    }
}

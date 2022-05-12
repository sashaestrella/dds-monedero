package dds.monedero.model;

import dds.monedero.exceptions.MontoNegativoException;

import java.time.LocalDate;

public abstract class Movimiento {
  LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  double monto;

  public double getMonto() {
    return monto;
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public void validarAplicacion(double saldoCuenta, long depositosDiarios, double limite) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  protected abstract double montoAplicable();
}

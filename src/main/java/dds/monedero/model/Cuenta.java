package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void aplicarMovimiento(Movimiento unMovimiento) {
    unMovimiento.validarAplicacion(saldo, cantidadDepositosDiarios(), limiteDiario());
    movimientos.add(unMovimiento);
    saldo += unMovimiento.montoAplicable();
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  private long cantidadDepositosDiarios() {
    return getMovimientos().stream().filter(movimiento -> movimiento.montoAplicable() > 0).count();
  }

  private double limiteDiario() {
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    return 1000 - montoExtraidoHoy;
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
            .filter(movimiento -> movimiento.montoAplicable() < 0 && movimiento.esDeLaFecha(fecha))
            .mapToDouble(Movimiento::getMonto)
            .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }
}

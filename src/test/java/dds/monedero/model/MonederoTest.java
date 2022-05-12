package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  static Deposito unDeposito;
  static Deposito otroDeposito;
  static Deposito tercerDeposito;

  static Extraccion extraccionMasDe1000;
  static Extraccion extraccionMayorSaldo;

  static Deposito depositoNegativo;
  static Extraccion extraccionNegativa;

  @BeforeEach
  void initEach() {
    cuenta = new Cuenta();
  }

  @BeforeAll
  static void initAll() {
    unDeposito = new Deposito(LocalDate.now(), 1000);
    otroDeposito = new Deposito(LocalDate.now(), 1500);
    tercerDeposito = new Deposito(LocalDate.now(), 2000);

    extraccionMasDe1000 = new Extraccion(LocalDate.now(), 2000);
    extraccionMayorSaldo = new Extraccion(LocalDate.now(), 400);

    depositoNegativo = new Deposito(LocalDate.now(), -300);
    extraccionNegativa = new Extraccion(LocalDate.now(), -500);
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.aplicarMovimiento(depositoNegativo));
  }

  @Test
  void TresDepositos() {
    cuenta.aplicarMovimiento(unDeposito);
    cuenta.aplicarMovimiento(otroDeposito);
    cuenta.aplicarMovimiento(tercerDeposito);
    assertEquals(cuenta.getSaldo(), 4500);
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.aplicarMovimiento(unDeposito);
      cuenta.aplicarMovimiento(unDeposito);
      cuenta.aplicarMovimiento(unDeposito);
      cuenta.aplicarMovimiento(unDeposito);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.aplicarMovimiento(extraccionMayorSaldo);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.aplicarMovimiento(extraccionMasDe1000);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.aplicarMovimiento(extraccionNegativa));
  }

}
package com.teksystems.tools;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CalculationToolsTest {

  @Test
  public void testAdd() throws Exception {

    assertEquals(BigDecimal.valueOf(10.00).setScale(2),
                 CalculationTools.add(BigDecimal.ZERO, BigDecimal.TEN));
  }

  @Test
  public void testMultiply() throws Exception {
    assertEquals(BigDecimal.valueOf(10.00).setScale(2), CalculationTools.multiply(BigDecimal.TEN,
                                                                                  BigDecimal.ONE));
    assertEquals(BigDecimal.valueOf(10.00).setScale(2), CalculationTools.multiply(
        BigDecimal.valueOf(5.00), 2));

  }


  @Test
  public void testCalculatePercentage() throws Exception {
    assertEquals(BigDecimal.valueOf(0.80).setScale(2), CalculationTools.calculatePercentage(
        BigDecimal.TEN, BigDecimal.valueOf(8.00)));
  }

  @Test
  public void testTotal() throws Exception {
    BigDecimal[] bigDecimals = new BigDecimal[]{
        BigDecimal.valueOf(10.00),
        BigDecimal.valueOf(10.00),
        BigDecimal.valueOf(10.00),
        BigDecimal.valueOf(10.00),
        BigDecimal.valueOf(10.00)
    };
    assertEquals(BigDecimal.valueOf(50.00).setScale(2),
                 CalculationTools.total(Arrays.asList(bigDecimals)));
  }
}
package com.teksystems.tools;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by ouazou on 2016-06-23.
 */
public class CalculationTools {

  public static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.ZERO.setScale(2);
  private static final BigDecimal DIVISOR_100 = BigDecimal.valueOf(100);
  private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
  private static final int DECIMALS = 2;

  public static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
    return rounded(amount1.add(amount2));

  }

  public static BigDecimal multiply(BigDecimal amount1, BigDecimal amount2) {
    return rounded(amount1.multiply(amount2));

  }

  public static BigDecimal multiply(BigDecimal amount1, int quantity) {
    return rounded(amount1.multiply(BigDecimal.valueOf(quantity)));

  }

  public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
    BigDecimal result = amount.multiply(percentage);
    result = result.divide(DIVISOR_100, DECIMALS, ROUNDING_MODE);
    return result;

  }

  private static BigDecimal rounded(BigDecimal amount) {
    return amount.setScale(DECIMALS, ROUNDING_MODE);

  }

  public static BigDecimal total(Collection<BigDecimal> amounts) {
    BigDecimal total = CalculationTools.BIG_DECIMAL_ZERO;
    for (BigDecimal taxAmount : amounts) {
      total = CalculationTools.add(total, taxAmount);
    }
    return total;
  }


}

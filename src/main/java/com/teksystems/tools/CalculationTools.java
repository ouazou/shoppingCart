package com.teksystems.tools;

import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-23.
 */
public class CalculationTools {

  private static final BigDecimal DIVISOR_100 = BigDecimal.valueOf(100);
  private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
  private static final int DECIMALS = 2;

  public static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
    return rounded(amount1.add(amount2));

  }

  public static BigDecimal multiply(BigDecimal amount1, BigDecimal amount2) {
    return rounded(amount1.multiply(amount2));

  }

  public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
    BigDecimal result = amount.multiply(percentage);
    result = result.divide(DIVISOR_100, ROUNDING_MODE);
    return rounded(result);

  }

  private static BigDecimal rounded(BigDecimal amount) {
    return amount.setScale(DECIMALS, ROUNDING_MODE);

  }


}

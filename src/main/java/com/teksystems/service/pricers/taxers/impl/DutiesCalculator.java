package com.teksystems.service.pricers.taxers.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.service.pricers.taxers.TaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;

/**
 * Implements ITaxCalculator
 * Calculate The Duties Taxes
 *
 * Created by ouazou on 2016-06-24.
 */
public class DutiesCalculator extends TaxCalculator {

  public static final BigDecimal PERCENTAGE_IMPORT_DUTY_TAXE = BigDecimal.valueOf(5.00);

  public DutiesCalculator(ICriteria ICriteria) {
    super(ICriteria);
  }

  @Override
  protected BigDecimal processItem(BillItem item) {
    return CalculationTools.calculatePercentage(item.getAmount(),PERCENTAGE_IMPORT_DUTY_TAXE);
  }

  @Override
  protected TaxType getTaxCalculatorType() {
    return TaxType.DUTIES;
  }

}

package com.teksystems.service.pricers.taxers.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.TaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;

/**
 * Implements ITaxCalculator
 * Calculate The State Taxes
 *
 * Created by ouazou on 2016-06-24.
 */
public class StateTaxCalculator extends TaxCalculator {

  private static final BigDecimal PERCENTAGE_SALES_TAXES = BigDecimal.valueOf(7.00);

  public StateTaxCalculator(ICriteria ICriteria) {
    super(ICriteria);
  }

  @Override
  protected BigDecimal processItem(BillItem item) {
    return CalculationTools.calculatePercentage(item.getAmount(),
                                                PERCENTAGE_SALES_TAXES);
  }

  @Override
  protected TaxType getTaxCalculatorType() {
    return TaxType.STATE;
  }

}

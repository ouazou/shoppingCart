package com.teksystems.service.pricers.taxers;

import com.teksystems.data.entity.BillItem;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * First Implementation of ITaxCalculator
 *
 * Created by ouazou on 2016-06-22.
 */
public abstract class TaxCalculator implements ITaxCalculator {

  private ITaxCalculator next;
  private ICriteria ICriteria;

  /**
   * Calcualte Taxes for a BillItem deligated to the Final class
   *
   * @return a BigDecimal
   */
  protected abstract BigDecimal processItem(BillItem item);

  /**
   * Return the Taxes Type Calculated by the final class
   *
   * @return TaxType Possible Values: TaxType.BASIC, TaxType.DUTIES, TaxType.STATE
   */
  protected abstract TaxType getTaxCalculatorType();


  public TaxCalculator(ICriteria ICriteria) {
    this.ICriteria = ICriteria;
  }

  @Override
  public void handle(Collection<BillItem> items, Map<TaxType, BigDecimal> taxesAmount) {
    BigDecimal calculatedAmount = CalculationTools.BIG_DECIMAL_ZERO;
    for (BillItem item : getSatisfiedItems(items)) {
      BigDecimal amount = processItem(item);
      calculatedAmount = CalculationTools.add(calculatedAmount, amount);
    }
    taxesAmount.put(getTaxCalculatorType(), calculatedAmount);
    if (next != null) {
      next.handle(items, taxesAmount);
    }
  }

  public ITaxCalculator getNext() {
    return next;

  }

  @Override
  public void setNext(ITaxCalculator next) {
    this.next = next;
  }


  private Collection<BillItem> getSatisfiedItems(Collection<BillItem> items) {
    return ICriteria.satisfy(items);
  }

}

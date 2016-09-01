package com.teksystems.service.pricers.taxers;

import com.teksystems.data.entity.BillItem;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * Interface to Taxes Calculator

 * Created by ouazou on 2016-06-22.
 */
public interface ITaxCalculator {

  /**
   * Calculate taxes
   * @param items
   * @param taxesAmount
   */
  void handle(Collection<BillItem> items, Map<TaxType, BigDecimal> taxesAmount);

  /**
   * Chain to the next Taxes Calculator
   * @param next
   */
  void setNext(ITaxCalculator next);
}

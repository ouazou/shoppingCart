package com.teksystems.service.pricer.impl;

import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.Pricer;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-24.
 */
public class StateTaxPricer extends Pricer {

  public static final BigDecimal PERCENTAGE_SALES_TAXES = BigDecimal.valueOf(7.00);

  public StateTaxPricer(Criteria criteria,
                        ISkuRepository skuRepository) {
    super(criteria, skuRepository);
  }

  @Override
  protected BigDecimal processItem(CartItem item) {
    return CalculationTools.calculatePercentage(item.getAmount(),
                                                PERCENTAGE_SALES_TAXES);
  }

}

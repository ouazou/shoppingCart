package com.teksystems.service.pricer.impl;

import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.IPricer;
import com.teksystems.service.pricer.Pricer;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-24.
 */
public class BasicTaxPricer extends Pricer {

  public static final BigDecimal PERCENTAGE_BASIC_TAX = BigDecimal.valueOf(8.00);

  public BasicTaxPricer(Criteria criteria,
                        ISkuRepository skuRepository) {
    super(criteria, skuRepository);
  }

  @Override
  protected BigDecimal processItem(CartItem item) {
    return CalculationTools.calculatePercentage(item.getAmount(),
                                                PERCENTAGE_BASIC_TAX);

  }


}

package com.teksystems.service.pricer.impl;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.Pricer;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-22.
 */
public class TotalAmountPricer extends Pricer {


  public TotalAmountPricer(Criteria criteria,
                           ISkuRepository skuRepository) {
    super(criteria, skuRepository);
  }

  protected BigDecimal processItem(CartItem item) {
    Sku sku = findBySkuname(item.getSkuName());
    BigDecimal itemAmount = CalculationTools.multiply(sku.getPrice(),
                                                      new BigDecimal(item.getQuantity()));
    item.setAmount(itemAmount);
    return itemAmount;
  }

}

package com.teksystems.service.pricers.taxers.criteria;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.Collection;

/**
 * Abstract Implementation of ICriteria
 *
 * Created by ouazou on 2016-06-24.
 */
public abstract class AbstractCriteria implements ICriteria {

  private ISkuRepository skuRepository;

  protected AbstractCriteria(ISkuRepository skuRepository) {
    this.skuRepository = skuRepository;
  }

  protected Sku findBySkuname(String skuName) {
    return skuRepository.findBySkuname(skuName);
  }

  @Override
  public abstract Collection<BillItem> satisfy(Collection<BillItem> items);
}

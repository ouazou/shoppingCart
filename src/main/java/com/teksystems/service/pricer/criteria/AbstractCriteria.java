package com.teksystems.service.pricer.criteria;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.Collection;

/**
 * Created by ouazou on 2016-06-24.
 */
public abstract class AbstractCriteria implements Criteria {

  private ISkuRepository skuRepository;

  public AbstractCriteria(ISkuRepository skuRepository) {
    this.skuRepository = skuRepository;
  }

  protected Sku findBySkuname(String skuname) {
    return skuRepository.findBySkuname(skuname);
  }

  @Override
  public abstract Collection<CartItem> satisfy(Collection<CartItem> items);
}

package com.teksystems.service.pricer.criteria.impl;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.criteria.AbstractCriteria;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ouazou on 2016-06-24.
 */
public class BasicSalesCriteria extends AbstractCriteria {

  public BasicSalesCriteria(ISkuRepository skuRepository) {
    super(skuRepository);
  }

  @Override
  public Collection<CartItem> satisfy(Collection<CartItem> items) {
    List<CartItem> satisfiedItems = new ArrayList<>();
    for (CartItem item : items) {
      Sku sku = findBySkuname(item.getSkuName());
      if (sku != null && sku.getType() != SkuType.FOOD) {
        satisfiedItems.add(item);
      }
    }
    return satisfiedItems;
  }
}

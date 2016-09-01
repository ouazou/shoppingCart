package com.teksystems.service.pricer.criteria.impl;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.criteria.AbstractCriteria;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ouazou on 2016-06-24.
 */
public class StateTaxCriteria extends AbstractCriteria {
  private static final Set<SkuType> PRODUCT_EXCEPTION= new HashSet<>();
  static {
    PRODUCT_EXCEPTION.add(SkuType.FOOD);
    PRODUCT_EXCEPTION.add(SkuType.BOOK);
    PRODUCT_EXCEPTION.add(SkuType.MEDICAL);
  }
  public StateTaxCriteria(ISkuRepository skuRepository) {
    super(skuRepository);
  }

  @Override
  public Collection<CartItem> satisfy(Collection<CartItem> items) {
    List<CartItem> satisfiedItems = new ArrayList<>();
    for (CartItem item : items) {
      Sku sku = findBySkuname(item.getSkuName());
      if (sku != null && !PRODUCT_EXCEPTION.contains(sku.getType())) {
        satisfiedItems.add(item);
      }
    }
    return satisfiedItems;
  }
}

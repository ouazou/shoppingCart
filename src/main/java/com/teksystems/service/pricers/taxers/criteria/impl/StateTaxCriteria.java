package com.teksystems.service.pricers.taxers.criteria.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.criteria.AbstractCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of ICriteria
 * Filter Collection of BillItem
 * Return all Items but of Type SkuType.FOOD && SkuType.BOOK && SkuType.MEDICAL
 *
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
  public Collection<BillItem> satisfy(Collection<BillItem> items) {
    List<BillItem> satisfiedItems = new ArrayList<>();
    for (BillItem item : items) {
      Sku sku = findBySkuname(item.getSkuName());
      if (sku != null && !PRODUCT_EXCEPTION.contains(sku.getType())) {
        satisfiedItems.add(item);
      }
    }
    return satisfiedItems;
  }
}

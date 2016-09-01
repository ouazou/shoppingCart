package com.teksystems.service.pricers.taxers.criteria.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.criteria.AbstractCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of ICriteria
 * Filter Collection of BillItem
 * Return all Items but of Type SkuType.FOOD
 *
 * Created by ouazou on 2016-06-24.
 */
public class BasicSalesCriteria extends AbstractCriteria {

  public BasicSalesCriteria(ISkuRepository skuRepository) {
    super(skuRepository);
  }

  @Override
  public Collection<BillItem> satisfy(Collection<BillItem> items) {
    List<BillItem> satisfiedItems = new ArrayList<>();
    for (BillItem item : items) {
      Sku sku = findBySkuname(item.getSkuName());
      if (sku != null && sku.getType() != SkuType.FOOD) {
        satisfiedItems.add(item);
      }
    }
    return satisfiedItems;
  }
}

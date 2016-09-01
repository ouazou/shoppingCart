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
 * Return all Items but of Type SkuType.MEDICAL and SKU imported
 *
 * Created by ouazou on 2016-06-24.
 */
public class ImportDutyCriteria extends AbstractCriteria {

  public ImportDutyCriteria(ISkuRepository skuRepository) {
    super(skuRepository);
  }

  @Override
  public Collection<BillItem> satisfy(Collection<BillItem> items) {
    List<BillItem> satisfiedItems = new ArrayList<>();
    for (BillItem item : items) {
      Sku sku = findBySkuname(item.getSkuName());
      if (sku != null && sku.isImported() && sku.getType() != SkuType.MEDICAL) {
        satisfiedItems.add(item);
      }
    }
    return satisfiedItems;
  }
}

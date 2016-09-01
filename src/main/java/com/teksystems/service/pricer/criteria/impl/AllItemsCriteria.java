package com.teksystems.service.pricer.criteria.impl;

import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.Collection;

/**
 * Created by ouazou on 2016-06-24.
 */
public class AllItemsCriteria implements Criteria{

  @Override
  public Collection<CartItem> satisfy(Collection<CartItem> items) {
    return items;
  }
}

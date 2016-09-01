package com.teksystems.service.pricer.criteria;

import com.teksystems.service.shoppingcart.CartItem;

import java.util.Collection;

/**
 * Created by ouazou on 2016-06-24.
 */
public interface Criteria {

  public Collection<CartItem> satisfy(Collection<CartItem> items);

}

package com.teksystems.service.shoppingcart.impl;

import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ouazou on 2016-06-22.
 */
public class ShoppingCart implements ICart {

  private Map<String, CartItem> items = new HashMap<>();

  public void addItem(String skuName, int quantity) {

    if (items.containsKey(skuName)) {
      CartItem item = items.get(skuName);
      item.setQuantity(item.getQuantity() + quantity);

    } else {
      items.put(skuName, new CartItem(skuName, quantity));
    }

  }

  @Override
  public Collection<CartItem> getItems() {
    return items.values();
  }

}

package com.teksystems.service.shoppingcart.impl;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of ICart
 * Created by ouazou on 2016-06-22.
 */
public class ShoppingCart implements ICart {

  private Map<String, CartItem> items = new LinkedHashMap<>();
  private ISkuRepository skuRepository;
  private BigDecimal total = BigDecimal.ZERO;

  public ShoppingCart(ISkuRepository skuRepository) {
    this.skuRepository = skuRepository;
  }

  @Override
  public void addItem(String skuName, int quantity) {
    Sku sku = skuRepository.findBySkuname(skuName);
    if (sku != null) {
      CartItem item;
      if (items.containsKey(skuName)) {
        item = items.get(skuName);
        item.setQuantity(item.getQuantity() + quantity);
      } else {
        item = new CartItem(skuName, quantity);
      }
      items.put(skuName, item);
    }
  }

  @Override
  public Collection<CartItem> getItems() {
    return items.values();
  }

}

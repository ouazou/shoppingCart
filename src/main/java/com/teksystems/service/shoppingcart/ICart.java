package com.teksystems.service.shoppingcart;

import java.util.Collection;

/**
 * Interface to define shopping-cart
 *
 * Created by ouazou on 2016-06-26.
 */
public interface ICart {

  /**
   * Add a product to the shopping cart
   * @param skuName the sku id of the product
   * @param quantity  the quantity of the product to add to the shopping-cart
   */
  void addItem(String skuName, int quantity);

  /**
   *
   * @return a Collection of CartItem of empty Collection
   */
  Collection<CartItem> getItems();

}

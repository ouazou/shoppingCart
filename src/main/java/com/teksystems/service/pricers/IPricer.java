package com.teksystems.service.pricers;

import com.teksystems.data.entity.Bill;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;

import java.util.Collection;

/**
 * Interface to the Pricer service
 *
 * Created by ouazou on 2016-06-25.
 */
public interface IPricer {

  /**
   * Build a Bill from a shopping-cart
   * @param cart Shopping-cart
   * @return Bill
   */
  Bill price(ICart cart);
}

package com.teksystems.service.pricer;

import com.teksystems.service.shoppingcart.CartItem;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by ouazou on 2016-06-22.
 */
public interface IPricer {

   void handle(Collection<CartItem> items);
   BigDecimal getCalculatedAmount();

   void setNext(IPricer next);
}

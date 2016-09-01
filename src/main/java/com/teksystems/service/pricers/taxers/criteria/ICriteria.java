package com.teksystems.service.pricers.taxers.criteria;

import com.teksystems.data.entity.BillItem;
import com.teksystems.service.shoppingcart.CartItem;

import java.util.Collection;

/**
 * Interface to Criteria classes
 *
 * Created by ouazou on 2016-06-24.
 */
public interface ICriteria {

  /**
   *  Filter a Collection of BillItem upon condition implemented in the final
   *  Classes
   * @param items
   * @return
   */
  public Collection<BillItem> satisfy(Collection<BillItem> items);

}

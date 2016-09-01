package com.teksystems.service.pricer.criteria.impl;

import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class AllItemsCriteriaTest {

  private Criteria criteria;

  @Before
  public void setUp() throws Exception {

    criteria = new AllItemsCriteria();
  }

  @Test
  public void testSatisfy() throws Exception {

    List<CartItem> items = new ArrayList<>();
    items.add(new CartItem("SKU01", 1));
    Collection<CartItem> satisfiedItems = criteria.satisfy(items);
    assertArrayEquals(items.toArray(new CartItem[]{}), satisfiedItems.toArray(new CartItem[]{}));
  }
}
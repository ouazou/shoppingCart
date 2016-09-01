package com.teksystems.service.shoppingcart;

import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartTest {

  private ShoppingCart cart;

  @Before
  public void setUp() throws Exception {
    cart = new ShoppingCart();
  }

  @Test
  public void testCartNotNull() throws Exception {
    assertNotNull(cart);
  }

  @Test
  public void testAddItem() throws Exception {
    cart.addItem("prod",1);
    assertEquals(1,cart.getItems().size());
  }

  @Test
  public void testGetItems() throws Exception {
    assertNotNull(cart.getItems());
  }
}
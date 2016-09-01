package com.teksystems.service.shoppingcart;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CartItemTest {

  private CartItem cartItem;

  @Before
  public void setUp() throws Exception {
    cartItem = new CartItem("sku", 5);
  }

  @Test
  public void testCartItemNotNull() throws Exception {
    assertNotNull(cartItem);
  }

  @Test
  public void testGetSkuName() throws Exception {

    assertEquals("sku", cartItem.getSkuName());
  }

  @Test
  public void testSetSkuName() throws Exception {
    cartItem.setSkuName("prod0");
    assertEquals("prod0", cartItem.getSkuName());

  }

  @Test
  public void testGetQuantity() throws Exception {
    assertEquals(5, cartItem.getQuantity());

  }

  @Test
  public void testSetQuantity() throws Exception {
    cartItem.setQuantity(10);
    assertEquals(10, cartItem.getQuantity());

  }
}
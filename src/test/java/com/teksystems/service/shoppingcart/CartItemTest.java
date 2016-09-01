package com.teksystems.service.shoppingcart;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

  @Test
  public void testEqual() throws Exception {
    assertEquals(cartItem, cartItem);
    assertEquals(new CartItem("sku", 5), cartItem);
    assertEquals(new CartItem("sku", 5).hashCode(), cartItem.hashCode());
    assertFalse(cartItem.equals(new CartItem("sku01", 5)));
    assertFalse(cartItem.equals(new CartItem("sku", 10)));
    assertFalse(cartItem.equals(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionSkuNull() throws Exception {
    cartItem = new CartItem(null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionSkuEmpy() throws Exception {
    cartItem = new CartItem("", 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalArgumentExceptionSkuQuantityNegative() throws Exception {
    cartItem = new CartItem("sku", -1);
  }
}
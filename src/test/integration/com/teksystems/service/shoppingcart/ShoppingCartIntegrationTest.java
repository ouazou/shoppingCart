package com.teksystems.service.shoppingcart;

import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShoppingCartIntegrationTest {

  private ShoppingCart cart;
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    repo = new SkuRepository();
    cart = new ShoppingCart(repo);
  }


  @Test
  public void testNoneExistingSku() throws Exception {
    cart.addItem("SKU01", 1);
    cart.addItem("SKU02", 1);
    cart.addItem("SKU11", 1);
    cart.addItem("SKU12", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU22", 1);
    cart.addItem("SKU31", 1);
    cart.addItem("SKU32", 1);
    cart.addItem("SKU41", 1);
    cart.addItem("SKU42", 1);

    CartItem[] items = cart.getItems().toArray(new CartItem[]{});

    assertEquals(10, items.length);
    assertEquals(new CartItem("SKU01", 1), items[0]);

    assertEquals(new CartItem("SKU02", 1), items[1]);
    assertEquals(new CartItem("SKU11", 1), items[2]);

    assertEquals(new CartItem("SKU12", 1), items[3]);
    assertEquals(new CartItem("SKU21", 1), items[4]);

    assertEquals(new CartItem("SKU22", 1), items[5]);
    assertEquals(new CartItem("SKU31", 1), items[6]);

    assertEquals(new CartItem("SKU32", 1), items[7]);
    assertEquals(new CartItem("SKU41", 1), items[8]);

    assertEquals(new CartItem("SKU42", 1), items[9]);
  }

  @Test
  public void testUpdateCarItemQuantity() throws Exception {
    cart.addItem("SKU01", 1);
    cart.addItem("SKU02", 1);
    cart.addItem("SKU11", 1);
    cart.addItem("SKU12", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU22", 1);
    cart.addItem("SKU31", 1);
    cart.addItem("SKU32", 1);
    cart.addItem("SKU41", 1);
    cart.addItem("SKU42", 1);
    cart.addItem("SKU01", 1);
    cart.addItem("SKU11", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU31", 1);
    cart.addItem("SKU41", 1);

    CartItem[] items = cart.getItems().toArray(new CartItem[]{});

    assertEquals(10, items.length);
    assertEquals(new CartItem("SKU01", 2), items[0]);

    assertEquals(new CartItem("SKU02", 1), items[1]);
    assertEquals(new CartItem("SKU11", 2), items[2]);

    assertEquals(new CartItem("SKU12", 1), items[3]);
    assertEquals(new CartItem("SKU21", 2), items[4]);

    assertEquals(new CartItem("SKU22", 1), items[5]);
    assertEquals(new CartItem("SKU31", 2), items[6]);

    assertEquals(new CartItem("SKU32", 1), items[7]);
    assertEquals(new CartItem("SKU41", 2), items[8]);

    assertEquals(new CartItem("SKU42", 1), items[9]);
  }

}
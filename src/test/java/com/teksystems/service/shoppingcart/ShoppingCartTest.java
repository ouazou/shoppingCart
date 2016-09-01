package com.teksystems.service.shoppingcart;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ShoppingCartTest {

  private ShoppingCart cart;
  @Mock
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    cart = new ShoppingCart(repo);
  }

  @Test
  public void testCartNotNull() throws Exception {
    assertNotNull(cart);
  }

  @Test
  public void testNoneExistingSku() throws Exception {
    when(repo.findBySkuname("SKU01")).thenReturn(null);
    cart.addItem("SKU01",1);
    assertEquals(0,cart.getItems().size());
  }
  @Test
  public void testAddItem() throws Exception {
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK,"JAVA",false,BigDecimal.TEN));
    cart.addItem("SKU01",1);
    assertEquals(1,cart.getItems().size());
  }

  @Test
  public void testGetItems() throws Exception {
    assertNotNull(cart.getItems());
  }

  @Test
  public void testAddTheSameSkuItem() throws Exception {
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK,"JAVA",false,BigDecimal.TEN));
    cart.addItem("SKU01",1);
    cart.addItem("SKU01",1);
    assertEquals(1,cart.getItems().size());
    CartItem item=cart.getItems().toArray(new CartItem[]{})[0];
    assertNotNull(item);
    assertEquals("SKU01",item.getSkuName());
    assertEquals(2,item.getQuantity());
  }
}
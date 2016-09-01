package com.teksystems.tools;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BillingToolTest {

  @Mock
  private ICart cart;
  @Mock
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

  }

  @Test
  public void testCreateBillFromCart() throws Exception {

    CartItem[] items=new CartItem[]{
        new CartItem("SKU01",1)
    };
    when(cart.getItems()).thenReturn(Arrays.asList(items));
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK,"JAVA",false,BigDecimal.TEN));
    Bill bill=BillingTool.createBill(cart,repo);
    assertEquals(1,bill.getItems().size());
    assertEquals(BigDecimal.valueOf(10.00).setScale(2),bill.getSubTotal());

  }
  @Test
  public void testCreateBillFromCartButSkuHasbeenDeleted() throws Exception {

    CartItem[] items=new CartItem[]{
        new CartItem("SKU01",1)
    };
    when(cart.getItems()).thenReturn(Arrays.asList(items));
    when(repo.findBySkuname("SKU01")).thenReturn(null);
    Bill bill=BillingTool.createBill(cart,repo);
    assertEquals(0,bill.getItems().size());
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),bill.getSubTotal());

  }

  @Test
  public void testConvertMap() throws Exception {
    Map<TaxType, BigDecimal> sourceMap = new HashMap<>();
    sourceMap.put(TaxType.BASIC, BigDecimal.ZERO);
    sourceMap.put(TaxType.DUTIES, BigDecimal.ONE);
    sourceMap.put(TaxType.STATE, BigDecimal.TEN);
    Map<String, BigDecimal> targetmap = BillingTool.ConvertMap(sourceMap);

    assertTrue(targetmap.get(TaxType.BASIC.toString()) != null);
    assertEquals(BigDecimal.ZERO, targetmap.get(TaxType.BASIC.toString()));

    assertTrue(targetmap.get(TaxType.DUTIES.toString()) != null);
    assertEquals(BigDecimal.ONE, targetmap.get(TaxType.DUTIES.toString()));

    assertTrue(targetmap.get(TaxType.STATE.toString()) != null);
    assertEquals(BigDecimal.TEN, targetmap.get(TaxType.STATE.toString()));
  }
}
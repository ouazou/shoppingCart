package com.teksystems.service.pricers;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.tools.CalculationTools;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BillPricerTest {

  @Mock
  ISkuRepository skuRepo;
  IPricer billPricer;
  ITaxCalculator basicTaxCalculator = mock(ITaxCalculator.class);
  ITaxCalculator dutiesTaxCalculator = mock(ITaxCalculator.class);
  ITaxCalculator statesTaxCalculator = mock(ITaxCalculator.class);
  @Mock
  ICart cart;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    ITaxCalculator[] iTaxCalculators = new ITaxCalculator[]{
        basicTaxCalculator,
        dutiesTaxCalculator,
        statesTaxCalculator
    };
    billPricer = new BillPricer(skuRepo, Arrays.asList(iTaxCalculators));
  }

  @Test
  public void testPriceWithAllSku() throws Exception {
    CartItem[] catItems = new CartItem[]{
        new CartItem("SKU01", 1),
        new CartItem("SKU02", 1),
        new CartItem("SKU11", 1),
        new CartItem("SKU12", 1),
        new CartItem("SKU21", 1),
        new CartItem("SKU22", 1),
        new CartItem("SKU31", 1),
        new CartItem("SKU32", 1),
        new CartItem("SKU41", 1),
        new CartItem("SKU42", 1),
    };
    when(cart.getItems()).thenReturn(Arrays.asList(catItems));
    when(skuRepo.findBySkuname("SKU01")).thenReturn(
        new Sku("SKU01", SkuType.BOOK, "Java Rules", false,
                BigDecimal.valueOf(9.99)));
    when(skuRepo.findBySkuname("SKU02")).thenReturn(
        new Sku("SKU02", SkuType.BOOK, "Groovy Rules", true,
                BigDecimal.valueOf(12.99)));
    when(skuRepo.findBySkuname("SKU11")).thenReturn(
        new Sku("SKU11", SkuType.TECH, "Gadget 1", false, BigDecimal.valueOf(189.89)));
    when(skuRepo.findBySkuname("SKU12")).thenReturn(
        new Sku("SKU12", SkuType.TECH, "Gadget 2", true, BigDecimal.valueOf(2999.97)));
    when(skuRepo.findBySkuname("SKU21"))
        .thenReturn(new Sku("SKU21", SkuType.FOOD, "Bread", false, BigDecimal.valueOf(1.95)));
    when(skuRepo.findBySkuname("SKU22"))
        .thenReturn(new Sku("SKU22", SkuType.FOOD, "Chocolate", true, BigDecimal.valueOf(
            2.98)));
    when(skuRepo.findBySkuname("SKU31")).thenReturn(
        new Sku("SKU31", SkuType.WINE, "Rusty Grapes", false, BigDecimal.valueOf(9.79)));
    when(skuRepo.findBySkuname("SKU32")).thenReturn(
        new Sku("SKU32", SkuType.WINE, "Groovy Grove", true, BigDecimal.valueOf(19.29)));
    when(skuRepo.findBySkuname("SKU41"))
        .thenReturn(new Sku("SKU41", SkuType.MEDICAL, "Aspirin", false, BigDecimal.valueOf(4.79)));
    when(skuRepo.findBySkuname("SKU42")).thenReturn(
        new Sku("SKU42", SkuType.MEDICAL, "Heart Pills", true, BigDecimal.valueOf(17.82)));

    Bill bill = billPricer.price(cart);

    assertEquals(10, bill.getItems().size());
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));

    assertEquals(BigDecimal.valueOf(0.00).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(3269.46).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(3269.46).setScale(2), bill.getTotal());

    Map<TaxType, BigDecimal> taxesAmounts = new HashMap<>();
    taxesAmounts.put(TaxType.BASIC, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.DUTIES, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.STATE, CalculationTools.BIG_DECIMAL_ZERO);

    verify(basicTaxCalculator, atLeastOnce()).setNext(dutiesTaxCalculator);
    verify(dutiesTaxCalculator, atLeastOnce()).setNext(statesTaxCalculator);
    verify(basicTaxCalculator, atLeastOnce()).handle(bill.getItems(), taxesAmounts);
  }

  @Test
  public void testTaxesCalculatorListNull() throws Exception {
    CartItem[] catItems = new CartItem[]{
        new CartItem("SKU01", 1),
    };
    when(cart.getItems()).thenReturn(Arrays.asList(catItems));
    when(skuRepo.findBySkuname("SKU01")).thenReturn(
        new Sku("SKU01", SkuType.BOOK, "Java Rules", false,
                BigDecimal.valueOf(9.99)));
    ((BillPricer) billPricer).setTaxCalculatorList(null);

    Bill bill = billPricer.price(cart);

    assertEquals(1, bill.getItems().size());
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(9.99).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(9.99).setScale(2), bill.getTotal());

    Map<TaxType, BigDecimal> taxesAmounts = new HashMap<>();
    taxesAmounts.put(TaxType.BASIC, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.DUTIES, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.STATE, CalculationTools.BIG_DECIMAL_ZERO);

    verify(basicTaxCalculator, atMost(1)).setNext(dutiesTaxCalculator);
    verify(dutiesTaxCalculator, atMost(1)).setNext(statesTaxCalculator);
    verify(basicTaxCalculator, atMost(1)).handle(bill.getItems(), taxesAmounts);
  }

  @Test
  public void testTaxesCalculatorListEpty() throws Exception {
    CartItem[] catItems = new CartItem[]{
        new CartItem("SKU01", 1),
    };
    when(cart.getItems()).thenReturn(Arrays.asList(catItems));
    when(skuRepo.findBySkuname("SKU01")).thenReturn(
        new Sku("SKU01", SkuType.BOOK, "Java Rules", false,
                BigDecimal.valueOf(9.99)));
    ((BillPricer) billPricer).setTaxCalculatorList(Arrays.asList(new ITaxCalculator[]{}));

    Bill bill = billPricer.price(cart);

    assertEquals(1, bill.getItems().size());
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(9.99).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(9.99).setScale(2), bill.getTotal());

    Map<TaxType, BigDecimal> taxesAmounts = new HashMap<>();
    taxesAmounts.put(TaxType.BASIC, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.DUTIES, CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.STATE, CalculationTools.BIG_DECIMAL_ZERO);

    verify(basicTaxCalculator, atMost(1)).setNext(dutiesTaxCalculator);
    verify(dutiesTaxCalculator, atMost(1)).setNext(statesTaxCalculator);
    verify(basicTaxCalculator, atMost(1)).handle(bill.getItems(), taxesAmounts);
  }

}
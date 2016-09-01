package com.teksystems.service.pricers.taxers.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DutiesCalculatorTest {

  private DutiesCalculator taxCalculator;
  @Mock
  private ICriteria criteria;
  @Mock
  private ISkuRepository repo;
  @Mock
  private ITaxCalculator nexTaxCalculator;

  private List<BillItem> items = Arrays.asList(
      new BillItem[]{
          new BillItem("SKU01", 1, BigDecimal.ZERO)
      }
  );


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    taxCalculator = new DutiesCalculator(criteria);
    taxCalculator.setNext(nexTaxCalculator);
    when(criteria.satisfy(items)).thenReturn(items);
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK, "BOOK", false,
                                                         BigDecimal.valueOf(10.00)));

  }

  @Test
  public void testHandle() throws Exception {
    List<BillItem> items = Arrays.asList(new BillItem[]{new BillItem("SKU01", 1, BigDecimal.TEN)});
    Map<TaxType, BigDecimal> taxes = new HashMap<TaxType, BigDecimal>();

    when(criteria.satisfy(items)).thenReturn(items);
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK, "BOOK", false,
                                                         BigDecimal.valueOf(10.00)));
    when(criteria.satisfy(items)).thenReturn(items);
    taxCalculator.handle(items, taxes);
    assertEquals(BigDecimal.valueOf(0.50).setScale(2), taxes.get(TaxType.DUTIES));
    verify(nexTaxCalculator, times(1)).handle(items, taxes);
  }

  @Test
  public void testHandleWithNextTaxCalculatorNull() throws Exception {
    List<BillItem> items = Arrays.asList(new BillItem[]{new BillItem("SKU01", 1, BigDecimal.TEN)});
    Map<TaxType, BigDecimal> taxes = new HashMap<TaxType, BigDecimal>();

    when(criteria.satisfy(items)).thenReturn(items);
    when(repo.findBySkuname("SKU01")).thenReturn(new Sku("SKU01", SkuType.BOOK, "BOOK", false,
                                                         BigDecimal.valueOf(10.00)));
    taxCalculator.setNext(null);
    taxCalculator.handle(items, taxes);
    assertEquals(BigDecimal.valueOf(0.50).setScale(2), taxes.get(TaxType.DUTIES));
    verify(nexTaxCalculator, times(0)).handle(items, taxes);
  }

  @Test
  public void testProcessItem() throws Exception {
    items.get(0).setAmount(BigDecimal.valueOf(10.00));
    assertEquals(BigDecimal.valueOf(0.50).setScale(2), taxCalculator.processItem(items.get(0)));
  }

  @Test
  public void testGetTaxCalculatorType() throws Exception {

    assertEquals(TaxType.DUTIES, taxCalculator.getTaxCalculatorType());
  }


}
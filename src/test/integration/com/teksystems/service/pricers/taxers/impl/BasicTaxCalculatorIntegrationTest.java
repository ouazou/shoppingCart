package com.teksystems.service.pricers.taxers.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.BasicSalesCriteria;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class BasicTaxCalculatorIntegrationTest {

  private BasicTaxCalculator taxCalculator;

  private ICriteria criteria;
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    repo = new SkuRepository();
    criteria = new BasicSalesCriteria(repo);
    taxCalculator = new BasicTaxCalculator(criteria);
    taxCalculator.setNext(new ITaxCalculator() {
      @Override
      public void handle(Collection<BillItem> items, Map<TaxType, BigDecimal> taxesAmount) {

      }

      @Override
      public void setNext(ITaxCalculator next) {

      }
    });
  }

  @Test
  public void testHandleAllSkus() throws Exception {
    List<BillItem> items = Arrays.asList(
        new BillItem[]{
            new BillItem("SKU01", 1, BigDecimal.TEN),
            new BillItem("SKU02", 1, BigDecimal.TEN),
            new BillItem("SKU11", 1, BigDecimal.TEN),
            new BillItem("SKU12", 1, BigDecimal.TEN),
            new BillItem("SKU21", 1, BigDecimal.TEN),
            new BillItem("SKU22", 1, BigDecimal.TEN),
            new BillItem("SKU31", 1, BigDecimal.TEN),
            new BillItem("SKU32", 1, BigDecimal.TEN),
            new BillItem("SKU41", 1, BigDecimal.TEN),
            new BillItem("SKU42", 1, BigDecimal.TEN)
        }
    );
    Map<TaxType, BigDecimal> taxes = new HashMap<TaxType, BigDecimal>();
    taxCalculator.handle(items, taxes);
    assertEquals(BigDecimal.valueOf(6.40).setScale(2), taxes.get(TaxType.BASIC));

  }


  @Test
  public void testHandleOnlyExceptedSkus() throws Exception {
    List<BillItem> items = Arrays.asList(
        new BillItem[]{
            new BillItem("SKU21", 1, BigDecimal.TEN),
            new BillItem("SKU22", 1, BigDecimal.TEN),
        }
    );
    Map<TaxType, BigDecimal> taxes = new HashMap<TaxType, BigDecimal>();
    taxCalculator.handle(items, taxes);
    assertEquals(BigDecimal.valueOf(0.00).setScale(2), taxes.get(TaxType.BASIC));

  }


}
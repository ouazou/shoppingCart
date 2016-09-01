package com.teksystems.service.pricers.taxers.criteria.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateTaxICriteriaIntegrationTest {

  private ICriteria ICriteria;
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {

    repo = new SkuRepository();
    ICriteria = new StateTaxCriteria(repo);
  }

  @Test
  public void testSatisfyAllSKU() throws Exception {
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
    Collection<BillItem> satisfiedItems = ICriteria.satisfy(items);
    assertEquals(4, satisfiedItems.size());
    assertTrue(satisfiedItems.contains(new BillItem("SKU11", 1, BigDecimal.TEN)));
    assertTrue(satisfiedItems.contains(new BillItem("SKU12", 1, BigDecimal.TEN)));
    assertTrue(satisfiedItems.contains(new BillItem("SKU31", 1, BigDecimal.TEN)));
    assertTrue(satisfiedItems.contains(new BillItem("SKU32", 1, BigDecimal.TEN)));
  }

  @Test
  public void testSatisfyOnlyExceptedSkus() throws Exception {
    List<BillItem> items = Arrays.asList(
        new BillItem[]{
            new BillItem("SKU01", 1, BigDecimal.TEN),
            new BillItem("SKU02", 1, BigDecimal.TEN),
            new BillItem("SKU21", 1, BigDecimal.TEN),
            new BillItem("SKU22", 1, BigDecimal.TEN),
            new BillItem("SKU41", 1, BigDecimal.TEN),
            new BillItem("SKU42", 1, BigDecimal.TEN)
        }
    );
    Collection<BillItem> satisfiedItems = ICriteria.satisfy(items);
    assertEquals(0, satisfiedItems.size());

  }

}
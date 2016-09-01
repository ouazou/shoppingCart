package com.teksystems.service.pricers.taxers.criteria.impl;

import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StateTaxICriteriaTest {

  private ICriteria ICriteria;
  @Mock
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    ICriteria = new StateTaxCriteria(repo);
  }

  @Test
  public void testSatisfyWhithAnoneExistingSKU() throws Exception {

    when(repo.findBySkuname("SKU01")).thenReturn(null);
    List<BillItem> items = new ArrayList<>();
    items.add(new BillItem("SKU01", 1, BigDecimal.ZERO));
    Collection<BillItem> satisfiedItems = ICriteria.satisfy(items);
    assertEquals(0, satisfiedItems.size());
  }

  @Test
  public void testSatisfyAndReturnAnEmptyCollection() throws Exception {

    when(repo.findBySkuname("SKU01"))
        .thenReturn(new Sku("SKU01", SkuType.BOOK, "JAVA", false, null));
    when(repo.findBySkuname("SKU02"))
        .thenReturn(new Sku("SKU02", SkuType.FOOD, "Chocolate", true, null));
    when(repo.findBySkuname("SKU03"))
        .thenReturn(new Sku("SKU03", SkuType.MEDICAL, "Aspirin", true, null));
    List<BillItem> items = new ArrayList<>();
    items.add(new BillItem("SKU01", 1,BigDecimal.ZERO));
    items.add(new BillItem("SKU02", 1,BigDecimal.ZERO));
    items.add(new BillItem("SKU03", 1,BigDecimal.ZERO));
    Collection<BillItem> satisfiedItems = ICriteria.satisfy(items);
    assertEquals(0, satisfiedItems.size());
  }

  @Test
  public void testSatisfyAndReturnAnNoneEmptyCollection() throws Exception {

    when(repo.findBySkuname("SKU01"))
        .thenReturn(new Sku("SKU01", SkuType.TECH, "Gadget 1", false, null));
    when(repo.findBySkuname("SKU02"))
        .thenReturn(new Sku("SKU02", SkuType.FOOD, "Chocolate", true, null));
    when(repo.findBySkuname("SKU03"))
        .thenReturn(new Sku("SKU03", SkuType.MEDICAL, "Aspirin", true, null));
    List<BillItem> items = new ArrayList<>();
    items.add(new BillItem("SKU01", 1,BigDecimal.ZERO));
    items.add(new BillItem("SKU02", 1,BigDecimal.ZERO));
    items.add(new BillItem("SKU03", 1,BigDecimal.ZERO));
    Collection<BillItem> satisfiedItems = ICriteria.satisfy(items);
    assertEquals(1, satisfiedItems.size());
  }
}
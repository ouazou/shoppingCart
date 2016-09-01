package com.teksystems.service.pricer.criteria.impl;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StateTaxCriteriaTest {

  private Criteria criteria;
  @Mock
  private ISkuRepository repo;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    criteria = new StateTaxCriteria(repo);
  }

  @Test
  public void testSatisfyWhithAnoneExistingSKU() throws Exception {

    when(repo.findBySkuname("SKU01")).thenReturn(null);
    List<CartItem> items = new ArrayList<>();
    items.add(new CartItem("SKU01", 1));
    Collection<CartItem> satisfiedItems = criteria.satisfy(items);
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
    List<CartItem> items = new ArrayList<>();
    items.add(new CartItem("SKU01", 1));
    items.add(new CartItem("SKU02", 1));
    items.add(new CartItem("SKU03", 1));
    Collection<CartItem> satisfiedItems = criteria.satisfy(items);
    assertEquals(0, satisfiedItems.size());
  }
}
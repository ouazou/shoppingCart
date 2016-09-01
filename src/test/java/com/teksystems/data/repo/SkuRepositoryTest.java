package com.teksystems.data.repo;

import com.teksystems.data.repo.impl.SkuRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SkuRepositoryTest {

  private ISkuRepository skuRepository;

  @Before
  public void setup() {
    this.skuRepository = new SkuRepository();
  }

  @Test
  public void validate_that_the_skus_are_all_in_the_inmemory_datastore() {
    String[] skus = {"SKU01", "SKU02", "SKU11", "SKU12", "SKU21",
                     "SKU22", "SKU31", "SKU32", "SKU41", "SKU42"};
    for (String sku : skus) {
      assertNotNull(this.skuRepository.findBySkuname(sku));
    }
  }

  @Test
  public void validate_that_the_unknown_sku_name_is_null() {
    assertNull(this.skuRepository.findBySkuname("UNKNOWN"));
  }
}

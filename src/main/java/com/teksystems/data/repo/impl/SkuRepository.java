package com.teksystems.data.repo.impl;


import com.teksystems.data.entity.Sku;
import com.teksystems.data.entity.SkuType;
import com.teksystems.data.repo.ISkuRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of ISkuRepository.
 */
public class SkuRepository implements ISkuRepository {

  /**
   * Our storage container.
   */
  private Map<String, Sku> skumap = new HashMap<String, Sku>();

  /**
   * Const that populates the instore map.
   */
  public SkuRepository() {
    this.skumap.put("SKU01",
                    new Sku("SKU01", SkuType.BOOK, "Java Rules", false, BigDecimal.valueOf(9.99)));
    this.skumap.put("SKU02", new Sku("SKU02", SkuType.BOOK, "Groovy Rules", true,
                                     BigDecimal.valueOf(12.99)));
    this.skumap.put("SKU11",
                    new Sku("SKU11", SkuType.TECH, "Gadget 1", false, BigDecimal.valueOf(189.89)));
    this.skumap.put("SKU12",
                    new Sku("SKU12", SkuType.TECH, "Gadget 2", true, BigDecimal.valueOf(2999.97)));
    this.skumap
        .put("SKU21", new Sku("SKU21", SkuType.FOOD, "Bread", false, BigDecimal.valueOf(1.95)));
    this.skumap
        .put("SKU22", new Sku("SKU22", SkuType.FOOD, "Chocolate", true, BigDecimal.valueOf(2.98)));
    this.skumap.put("SKU31", new Sku("SKU31", SkuType.WINE, "Rusty Grapes", false,
                                     BigDecimal.valueOf(9.79)));
    this.skumap.put("SKU32", new Sku("SKU32", SkuType.WINE, "Groovy Grove", true,
                                     BigDecimal.valueOf(19.29)));
    this.skumap.put("SKU41",
                    new Sku("SKU41", SkuType.MEDICAL, "Aspirin", false, BigDecimal.valueOf(4.79)));
    this.skumap.put("SKU42", new Sku("SKU42", SkuType.MEDICAL, "Heart Pills", true,
                                     BigDecimal.valueOf(17.82)));
  }

  @Override
  public Sku findBySkuname(final String skuname) {
    return this.skumap.get(skuname);
  }
}

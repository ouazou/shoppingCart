package com.teksystems.data.entity;

import java.math.BigDecimal;

/**
 * Created by rparry on 7/28/15.
 */
public class Sku {

  private String skuname;

  private SkuType type;

  private String brand;

  private boolean imported;

  private BigDecimal price;

  public Sku(String skuname, SkuType type, String brand, boolean imported, BigDecimal price) {
    this.skuname = skuname;
    this.type = type;
    this.brand = brand;
    this.imported = imported;
    this.price = price;
  }

  public String getSkuname() {
    return skuname;
  }

  public SkuType getType() {
    return type;
  }

  public String getBrand() {
    return brand;
  }

  public boolean isImported() {
    return imported;
  }

  public BigDecimal getPrice() {
    return price;
  }
}

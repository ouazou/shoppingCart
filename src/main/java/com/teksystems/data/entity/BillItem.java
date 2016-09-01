package com.teksystems.data.entity;

import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-22.
 */
public class BillItem {

  private String skuName;
  private int quantity;
  private BigDecimal amount;

  public BillItem() {
  }

  public BillItem(String skuName, int quantity, BigDecimal amount) {
    this.skuName = skuName;
    this.quantity = quantity;
    this.amount = amount;
  }

  public String getSkuName() {
    return skuName;
  }

  public void setSkuName(String skuName) {
    this.skuName = skuName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BillItem)) {
      return false;
    }

    BillItem billItem = (BillItem) o;

    if (quantity != billItem.quantity) {
      return false;
    }
    if (!amount.equals(billItem.amount)) {
      return false;
    }
    if (!skuName.equals(billItem.skuName)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = skuName.hashCode();
    result = 31 * result + quantity;
    result = 31 * result + amount.hashCode();
    return result;
  }
}

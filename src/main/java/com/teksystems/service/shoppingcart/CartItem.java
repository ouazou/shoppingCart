package com.teksystems.service.shoppingcart;


import java.math.BigDecimal;

/**
 * Created by ouazou on 2016-06-22.
 */
public class CartItem {

  private String skuName;
  private int quantity;
  private BigDecimal amount;


  public CartItem(String skuName, int quantity) {
   if(skuName==null || skuName.isEmpty()|| quantity<=0)
     throw new IllegalArgumentException("Illegal value for skuName, quantity");
    this.skuName = skuName;
    this.quantity = quantity;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CartItem cartItem = (CartItem) o;

    if (quantity != cartItem.quantity) {
      return false;
    }
    if (!skuName.equals(cartItem.skuName)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = skuName.hashCode();
    result = 31 * result + quantity;
    return result;
  }

}

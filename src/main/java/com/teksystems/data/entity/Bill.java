package com.teksystems.data.entity;

import com.teksystems.service.pricers.taxers.impl.TaxType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ouazou on 2016-06-22.
 */
public class Bill {

  List<BillItem> items;
  BigDecimal subTotal;
  BigDecimal total;
  Map<String, BigDecimal> taxes = new HashMap<>();

  public Bill() {
  }

  public Bill(List<BillItem> items, BigDecimal subTotal,
              Map<String, BigDecimal> taxes) {
    this.items = items;
    this.subTotal = subTotal;
    this.taxes = taxes;
  }

  public Collection<BillItem> getItems() {
    if (items == null) {
      items = new LinkedList<>();
    }
    return items;
  }

  public void setItems(List<BillItem> items) {
    this.items = items;
  }

  public BigDecimal getSubTotal() {
    if(subTotal==null)
      subTotal=BigDecimal.ZERO;
    return subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  public BigDecimal getTotal() {
    if(total==null)
      total=BigDecimal.ZERO;
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public Map<String, BigDecimal> getTaxes() {
    if (taxes == null) {
      taxes = new HashMap<>();
    }
    return taxes;
  }

  public void setTaxes(Map<String, BigDecimal> taxes) {
    this.taxes = taxes;
  }
}

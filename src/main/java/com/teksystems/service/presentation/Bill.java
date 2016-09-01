package com.teksystems.service.presentation;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.IPricer;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ouazou on 2016-06-26.
 */
public class Bill {

  public static String PRICER_TYPE_PRICES = "PRICES";
  public static String PRICER_TYPE_BASIC = "BASIC";
  public static String PRICER_TYPE_STATE = "STATE";
  public static String PRICER_TYPE_DUTIES = "DUTIES";

  private ISkuRepository repo;
  private ICart shoppingCart;
  private Map<String, IPricer> pricerMap = new HashMap<>();

  public Bill(ISkuRepository skuRepository, ICart shoppingCart,
              Map<String, IPricer> pricerMap) {
    this.repo = skuRepository;
    this.shoppingCart = shoppingCart;
    this.pricerMap = pricerMap;
  }

  public void printBill() {
    printBillBody();
    printbillTaxes();
    printBillTotal();


  }

  private void printBillBody() {
    for (CartItem item : shoppingCart.getItems()) {
      Sku sku = repo.findBySkuname(item.getSkuName());
      System.out.printf("%-6s%-15s%-20.2f\n", sku.getSkuname(), sku.getBrand(), item.getAmount().doubleValue());
    }
  }

  private void printbillTaxes() {
    System.out.println();
    System.out.printf("%-12s%-20.2f\n", "PRICES",
                      pricerMap.get(PRICER_TYPE_PRICES).getCalculatedAmount().doubleValue());
    System.out.println();
    System.out.println("TAXES AND DUTIES");

    System.out.printf("%-12s%-12.2f\n", "Basic:",
                      pricerMap.get(PRICER_TYPE_BASIC).getCalculatedAmount().doubleValue());
    System.out.printf("%-12s%-12.2f\n", "State:",
                      pricerMap.get(PRICER_TYPE_STATE).getCalculatedAmount().doubleValue());
    System.out.printf("%-12s%-12.2f\n", "Duties:",
                      pricerMap.get(PRICER_TYPE_DUTIES).getCalculatedAmount().doubleValue());
  }

  private void printBillTotal() {
    BigDecimal total = BigDecimal.valueOf(0.00);
    for (IPricer pricer : pricerMap.values()) {
      total = CalculationTools.add(total, pricer.getCalculatedAmount());
    }
    System.out.println();
    System.out.printf("%-12s%-12.2f\n","TOTAL:",total.doubleValue());

  }
}

package com.teksystems.presentation;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.impl.TaxType;

/**
 * Utility Class to print out a Bill Statement Created by ouazou on 2016-06-26.
 */
public class BillStatementPrinter {


  private ISkuRepository repo;

  public BillStatementPrinter(ISkuRepository skuRepository) {
    this.repo = skuRepository;

  }

  /**
   * Print a bill
   * @param bill
   */
  public void printBill(Bill bill) {
    printBillBody(bill);
    printBillTaxes(bill);
    printBillTotal(bill);


  }

  private void printBillBody(Bill bill) {

    for (BillItem item : bill.getItems()) {
      Sku sku = repo.findBySkuname(item.getSkuName());
      System.out.printf("%-6s%-15s%-20.2f\n", sku.getSkuname(), sku.getBrand(), item.getAmount());
    }
  }

  private void printBillTaxes(Bill bill) {
    System.out.println();
    System.out.printf("%-12s%-20.2f\n", "PRICES",
                      bill.getSubTotal());
    System.out.println();
    System.out.println("TAXES AND DUTIES");

    System.out.printf("%-12s%-12.2f\n", "Basic:",
                      bill.getTaxes().get(TaxType.BASIC.toString()));
    System.out.printf("%-12s%-12.2f\n", "State:",
                      bill.getTaxes().get(TaxType.STATE.toString()));
    System.out.printf("%-12s%-12.2f\n", "Duties:",
                      bill.getTaxes().get(TaxType.DUTIES.toString()));
  }

  private void printBillTotal(Bill bill) {

    System.out.println();
    System.out.printf("%-12s%-12.2f\n", "TOTAL:", bill.getTotal());
    System.out.println("--------");
    System.out.println();

  }


}

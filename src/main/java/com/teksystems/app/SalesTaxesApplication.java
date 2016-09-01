package com.teksystems.app;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.presentation.BillStatementPrinter;
import com.teksystems.service.pricers.BillPricer;
import com.teksystems.service.pricers.IPricer;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.BasicSalesCriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.ImportDutyCriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.StateTaxCriteria;
import com.teksystems.service.pricers.taxers.impl.BasicTaxCalculator;
import com.teksystems.service.pricers.taxers.impl.DutiesCalculator;
import com.teksystems.service.pricers.taxers.impl.StateTaxCalculator;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import java.util.Arrays;

/**
 * Created by ouazou on 2016-07-01.
 */
public class SalesTaxesApplication {

  private ICart cart;
  private ISkuRepository skuRepository;
  private ICriteria basicSalesICriteria;
  private ICriteria importDutyICriteria;
  private ICriteria stateTaxICriteria;
  private ITaxCalculator basicTaxPricer;
  private ITaxCalculator importDutyPricer;
  private ITaxCalculator stateTaxPricer;
  private IPricer BillPricer;

  private BillStatementPrinter billStatementPrinter;


  public void init() {
    skuRepository = new SkuRepository();
    cart = new ShoppingCart(skuRepository);
    basicSalesICriteria = new BasicSalesCriteria(skuRepository);
    importDutyICriteria = new ImportDutyCriteria(skuRepository);
    stateTaxICriteria = new StateTaxCriteria(skuRepository);
    basicTaxPricer = new BasicTaxCalculator(basicSalesICriteria);
    importDutyPricer = new DutiesCalculator(importDutyICriteria);
    stateTaxPricer = new StateTaxCalculator(stateTaxICriteria);
    BillPricer = new BillPricer(skuRepository, Arrays.asList(new ITaxCalculator[]{
        basicTaxPricer,
        importDutyPricer,
        stateTaxPricer
    }));
    billStatementPrinter = new BillStatementPrinter(skuRepository);

  }


  public void run() {
    cart.addItem("SKU01", 1);
    cart.addItem("SKU02", 1);
    cart.addItem("SKU11", 1);
    cart.addItem("SKU12", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU22", 1);
    cart.addItem("SKU31", 1);
    cart.addItem("SKU32", 1);
    cart.addItem("SKU41", 1);
    cart.addItem("SKU42", 1);

    Bill bill = BillPricer.price(cart);

    billStatementPrinter.printBill(bill);
  }

  public static void main(String[] args) {
    SalesTaxesApplication salesTaxesApp = new SalesTaxesApplication();
    salesTaxesApp.init();
    salesTaxesApp.run();
  }
}

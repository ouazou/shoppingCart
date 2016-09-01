package com.teksystems.service.shoppingcart;

import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.service.presentation.Bill;
import com.teksystems.service.pricer.IPricer;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.pricer.criteria.impl.AllItemsCriteria;
import com.teksystems.service.pricer.criteria.impl.BasicSalesCriteria;
import com.teksystems.service.pricer.criteria.impl.ImportDutyCriteria;
import com.teksystems.service.pricer.criteria.impl.StateTaxCriteria;
import com.teksystems.service.pricer.impl.BasicTaxPricer;
import com.teksystems.service.pricer.impl.ImportDutyPricer;
import com.teksystems.service.pricer.impl.StateTaxPricer;
import com.teksystems.service.pricer.impl.TotalAmountPricer;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ouazou on 2016-06-26.
 */
public class Testshop {

  private ShoppingCart cart;
  private ISkuRepository skuRepository = new SkuRepository();
  private Criteria allItemsCriteria = new AllItemsCriteria();
  private Criteria basicSalesCriteria = new BasicSalesCriteria(skuRepository);
  private Criteria importDutyCriteria = new ImportDutyCriteria(skuRepository);
  private Criteria stateTaxCriteria = new StateTaxCriteria(skuRepository);
  private IPricer totalAmountPicer = new TotalAmountPricer(allItemsCriteria, skuRepository);
  private IPricer basicTaxPricer = new BasicTaxPricer(basicSalesCriteria, skuRepository);
  private IPricer importDutyPricer = new ImportDutyPricer(importDutyCriteria, skuRepository);
  private IPricer stateTaxPricer = new StateTaxPricer(stateTaxCriteria, skuRepository);
  private Bill bill;


  @Before
  public void setUp() throws Exception {
    cart = new ShoppingCart();
    Map<String, IPricer> pricerMap = new HashMap<>();
    pricerMap.put(Bill.PRICER_TYPE_PRICES, totalAmountPicer);
    pricerMap.put(Bill.PRICER_TYPE_BASIC, basicTaxPricer);
    pricerMap.put(Bill.PRICER_TYPE_DUTIES, importDutyPricer);
    pricerMap.put(Bill.PRICER_TYPE_STATE, stateTaxPricer);
    totalAmountPicer.setNext(basicTaxPricer);
    basicTaxPricer.setNext(importDutyPricer);
    importDutyPricer.setNext(stateTaxPricer);
    bill = new Bill(skuRepository, cart, pricerMap);
  }

  @Test
  public void testBill() throws Exception {
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
    totalAmountPicer.handle(cart.getItems());
    bill.printBill();


  }
}

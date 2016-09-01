package com.teksystems.service.pricers;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.data.repo.impl.SkuRepository;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.criteria.ICriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.BasicSalesCriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.ImportDutyCriteria;
import com.teksystems.service.pricers.taxers.criteria.impl.StateTaxCriteria;
import com.teksystems.service.pricers.taxers.impl.BasicTaxCalculator;
import com.teksystems.service.pricers.taxers.impl.DutiesCalculator;
import com.teksystems.service.pricers.taxers.impl.StateTaxCalculator;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.service.shoppingcart.impl.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class BillPricerIntegrationTest {


  private ICart cart;
  private ISkuRepository skuRepository;
  private ICriteria basicSalesICriteria;
  private ICriteria importDutyICriteria;
  private ICriteria stateTaxICriteria;
  private ITaxCalculator basicTaxPricer;
  private ITaxCalculator importDutyPricer;
  private ITaxCalculator stateTaxPricer;
  private IPricer billPricer;

  @Before
  public void setUp() throws Exception {

    skuRepository = new SkuRepository();
    cart = new ShoppingCart(skuRepository);
    basicSalesICriteria = new BasicSalesCriteria(skuRepository);
    importDutyICriteria = new ImportDutyCriteria(skuRepository);
    stateTaxICriteria = new StateTaxCriteria(skuRepository);
    basicTaxPricer = new BasicTaxCalculator(basicSalesICriteria);
    importDutyPricer = new DutiesCalculator(importDutyICriteria);
    stateTaxPricer = new StateTaxCalculator(stateTaxICriteria);
    billPricer = new BillPricer(skuRepository, Arrays.asList(new ITaxCalculator[]{
        basicTaxPricer,
        importDutyPricer,
        stateTaxPricer
    }));

  }

  @Test
  public void testWithAllSku() throws Exception {
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

    Bill bill = billPricer.price(cart);
    assertEquals(10, bill.getItems().size());

    assertEquals(BigDecimal.valueOf(261.16).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(225.33).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(151.76).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(3269.46).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(3907.71).setScale(2), bill.getTotal());
  }

  @Test
  public void testDutiesEqualsZero() throws Exception {
    cart.addItem("SKU01", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU31", 1);
    cart.addItem("SKU41", 1);

    Bill bill = billPricer.price(cart);
    assertEquals(4, bill.getItems().size());

    assertEquals(BigDecimal.valueOf(1.96).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(0.69).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(26.52).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(29.17).setScale(2), bill.getTotal());

  }

  @Test
  public void testStatesTaxesEqualZero() throws Exception {
    cart.addItem("SKU01", 1);
    cart.addItem("SKU02", 1);
    cart.addItem("SKU21", 1);
    cart.addItem("SKU22", 1);
    cart.addItem("SKU41", 1);
    cart.addItem("SKU42", 1);

    Bill bill = billPricer.price(cart);
    assertEquals(6, bill.getItems().size());

    assertEquals(BigDecimal.valueOf(3.65).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.80).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(50.52).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(54.97).setScale(2), bill.getTotal());
  }

  @Test
  public void testBasicTaxesEqualZero() throws Exception {
    cart.addItem("SKU21", 1);
    cart.addItem("SKU22", 1);

    Bill bill = billPricer.price(cart);
    assertEquals(2, bill.getItems().size());

    assertEquals(BigDecimal.valueOf(0.00).setScale(2),
                 bill.getTaxes().get(TaxType.BASIC.toString()));
    assertEquals(BigDecimal.valueOf(0.00).setScale(2), bill.getTaxes().get(
        TaxType.STATE.toString()));
    assertEquals(BigDecimal.valueOf(0.15).setScale(2),
                 bill.getTaxes().get(TaxType.DUTIES.toString()));

    assertEquals(BigDecimal.valueOf(4.93).setScale(2), bill.getSubTotal());
    assertEquals(BigDecimal.valueOf(5.08).setScale(2), bill.getTotal());
  }


}
package com.teksystems.service.pricers;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.ITaxCalculator;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.ICart;
import com.teksystems.tools.BillingTool;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of IPricer
 *
 * Created by ouazou on 2016-06-25.
 */
public class BillPricer implements IPricer {

  /**
   * Top ITaxCalculator in the chain of ITaxCalculator
   */
  ITaxCalculator topTaxCalculator;
  ISkuRepository skuRepo;

  public BillPricer() {
  }

  /**
   *
   * @param skuRepo  SKU repository
   * @param taxCalculatorList List of ITaxCalculator
   */
  public BillPricer(ISkuRepository skuRepo, List<ITaxCalculator> taxCalculatorList) {
    this.skuRepo = skuRepo;
    chainTaxers(taxCalculatorList);
  }

  /**
   *  Build the chian of ITaxCalculator from list of ITaxCalculator
   * @param taxCalculatorList List of ITaxCalculator
   */
  private void chainTaxers(List<ITaxCalculator> taxCalculatorList) {
    if (taxCalculatorList != null && taxCalculatorList.size() != 0) {
      for (int i = 0; i < taxCalculatorList.size() - 1; i++) {
        taxCalculatorList.get(i).setNext(taxCalculatorList.get(i + 1));
      }
      this.topTaxCalculator = taxCalculatorList.get(0);
    }else{
      topTaxCalculator=null;
    }
  }

  public ISkuRepository getSkuRepo() {
    return skuRepo;
  }

  public void setSkuRepo(ISkuRepository skuRepo) {
    this.skuRepo = skuRepo;
  }

  public ITaxCalculator getTopTaxCalculator() {
    return topTaxCalculator;
  }

  public void setTaxCalculatorList(List<ITaxCalculator> taxCalculatorList) {
    chainTaxers(taxCalculatorList);
  }


  @Override
  public Bill price(ICart cart) {
    Map<TaxType, BigDecimal> taxesAmounts = new HashMap<>();
    taxesAmounts.put(TaxType.BASIC,CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.DUTIES,CalculationTools.BIG_DECIMAL_ZERO);
    taxesAmounts.put(TaxType.STATE,CalculationTools.BIG_DECIMAL_ZERO);
    Bill bill = BillingTool.createBill(cart, skuRepo);
    if (topTaxCalculator != null) {
      topTaxCalculator.handle(bill.getItems(), taxesAmounts);
    }
    Map<String, BigDecimal> taxes = BillingTool.ConvertMap(taxesAmounts);
    bill.getTaxes().putAll(taxes);
    bill.setTotal(CalculationTools.add(bill.getSubTotal(), CalculationTools.total(taxes.values())));
    return bill;
  }


}

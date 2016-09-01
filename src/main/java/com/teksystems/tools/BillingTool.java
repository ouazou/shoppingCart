package com.teksystems.tools;

import com.teksystems.data.entity.Bill;
import com.teksystems.data.entity.BillItem;
import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricers.taxers.impl.TaxType;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.service.shoppingcart.ICart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ouazou on 2016-07-02.
 */
public class BillingTool {

  public static Bill createBill(ICart cart, ISkuRepository skuRepository) {
    Bill bill = new Bill();
    BigDecimal subTotal = CalculationTools.BIG_DECIMAL_ZERO;
    for (CartItem cartItem : cart.getItems()) {
      Sku sku = skuRepository.findBySkuname(cartItem.getSkuName());
      if (sku != null) {
        BigDecimal amountPerItem = CalculationTools.multiply(
            sku.getPrice(),
            cartItem.getQuantity()
        );
        bill.getItems()
            .add(new BillItem(cartItem.getSkuName(), cartItem.getQuantity(),
                              amountPerItem));
        subTotal = CalculationTools.add(subTotal, amountPerItem);
      }
    }
    bill.setSubTotal(subTotal);
    return bill;
  }

  public static Map<String, BigDecimal> ConvertMap(Map<TaxType, BigDecimal> taxesAmounts) {
    Map<String, BigDecimal> taxesMap = new HashMap<>();
    for (Map.Entry<TaxType, BigDecimal> entry : taxesAmounts.entrySet()) {
      taxesMap.put(entry.getKey().toString(), entry.getValue());
    }
    return taxesMap;
  }

}

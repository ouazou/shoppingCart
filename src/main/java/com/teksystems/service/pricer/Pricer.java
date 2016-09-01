package com.teksystems.service.pricer;

import com.teksystems.data.entity.Sku;
import com.teksystems.data.repo.ISkuRepository;
import com.teksystems.service.pricer.criteria.Criteria;
import com.teksystems.service.shoppingcart.CartItem;
import com.teksystems.tools.CalculationTools;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by ouazou on 2016-06-22.
 */
public abstract class Pricer implements IPricer {

  private IPricer next;

  private Criteria criteria;

  private ISkuRepository skuRepository;

  private BigDecimal totalPricerAmount = BigDecimal.valueOf(0.00);

  protected abstract BigDecimal processItem(CartItem item);


  public Pricer(Criteria criteria,
                ISkuRepository skuRepository) {
    this.criteria = criteria;
    this.skuRepository = skuRepository;

  }

  @Override
  public void handle(Collection<CartItem> items) {
    for (CartItem item : getSatisfiedItems(items)) {
      BigDecimal amount = processItem(item);
      this.setAmount(CalculationTools.add(this.getCalculatedAmount(), amount));
    }

    if (next != null) {
      next.handle(items);
    }

  }

  public BigDecimal getCalculatedAmount() {
    return totalPricerAmount;
  }

  public IPricer getNext() {
    return next;

  }

  @Override
  public void setNext(IPricer next) {
    this.next = next;

  }

  protected Sku findBySkuname(String skuname) {
    return skuRepository.findBySkuname(skuname);

  }

  private void setAmount(BigDecimal amount) {
    this.totalPricerAmount = amount;
  }


  private Collection<CartItem> getSatisfiedItems(Collection<CartItem> items) {
    if (criteria != null) {
      return criteria.satisfy(items);
    }
    return items;

  }

}

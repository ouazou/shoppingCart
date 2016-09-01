package com.teksystems.data.repo;

import com.teksystems.data.entity.Sku;

/**
 * Interface to define working with the SkuRepository. Note that this is a very simple
 * implementation in order to have our developers quickly leverage a data store.
 *
 * Please note that we are using a Map in the background so that there is no dependancies required
 * for any particular data store.
 *
 * In short, this is a 'Mock-ish' to make things simple as possible.
 */
public interface ISkuRepository {

  /**
   * Retrieves and returns a Sku object when a skuname is sent in as the argument. Note that in the
   * event a sku is not found, this method will return NULL.
   *
   * @param skuname - The skuname from the table found in the documentation.
   * @return - A sku object or null.
   */
  Sku findBySkuname(String skuname);
}

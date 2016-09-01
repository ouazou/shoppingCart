package com.teksystems.data.repo

import com.teksystems.data.entity.Sku
import com.teksystems.data.repo.impl.SkuRepository
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test for the instore inmemory database.
 */
class SkuRepositorySpec extends Specification {

    ISkuRepository skuRepository

    def setup() {
        this.skuRepository = new SkuRepository()
    }

    @Unroll
    def "validate that the skus are all in the inmemory datastore"() {

        expect:
        Sku sku = this.skuRepository.findBySkuname(skuname)
        sku != null
        sku.skuname == skuname

        where:
        skuname << ["SKU01","SKU02","SKU11","SKU12","SKU21","SKU22","SKU31","SKU32","SKU41","SKU42"]
    }

    def "validate that an unknown skuname returns null"() {

        when:
        Sku shouldBeNull = this.skuRepository.findBySkuname("UNKNOWN")

        then:
        shouldBeNull == null
    }
}


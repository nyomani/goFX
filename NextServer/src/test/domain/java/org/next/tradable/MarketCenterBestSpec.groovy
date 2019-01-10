package org.next.tradable

import spock.lang.Specification

class MarketCenterBestSpec extends Specification {

    def "Test update"(){
        given:
            MarketCenterBest mc = new MarketCenterBest()
        when:
            mc.update("NYSE",Side.DEBIT.limit(1.1),300)
        then:
            mc.exchange=='NYSE'
            mc.price==Side.DEBIT.limit(1.1)
            mc.quantity==300
    }
}
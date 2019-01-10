package org.next.tradable

import spock.lang.Specification

class MarketCenterQuantitiesSpec extends Specification {

    def debit(p){
        Side.DEBIT.limit(p)
    }
    def "Test update new market center on empty list"(){
        given:
            MarketCenterQuantities mcQ = new MarketCenterQuantities(debit(1.1))
        when:
            mcQ.update('NYSE',debit(1.1),200)
        then:
            mcQ.price==debit(1.1)
            mcQ.quantities.size()==1
    }
    def "Test update new market center on a NON empty list"(){
        given:
            MarketCenterQuantities mcQ = new MarketCenterQuantities(debit(1.1))
        when:
            mcQ.update('NYSE',debit(1.1),200)
            mcQ.update('BATS',debit(1.1),300)
        then:
            mcQ.price==debit(1.1)
            mcQ.quantities.size()==2
    }
    def "Test update existing market center "(){
        given:
            MarketCenterQuantities mcQ = new MarketCenterQuantities(debit(1.1))
        when:
            mcQ.update('NYSE',debit(1.1),200)
            mcQ.update('NYSE',debit(1.1),300)
        then:
            mcQ.price==debit(1.1)
            mcQ.quantities.size()==1
    }
    def "Test update existing market center with zero quantity"(){
        given:
        MarketCenterQuantities mcQ = new MarketCenterQuantities(debit(1.1))
        when:
        mcQ.update('NYSE',debit(1.1),200)
        mcQ.update('NYSE',debit(1.1),0)
        then:
        mcQ.price==debit(1.1)
        mcQ.quantities.size()==0
    }
    def "Test remove an existing market center"(){
        given:
        MarketCenterQuantities mcQ = new MarketCenterQuantities(debit(1.1))
        when:
        mcQ.update('NYSE',debit(1.1),200)
        mcQ.remove('NYSE')
        then:
        mcQ.price==debit(1.1)
        mcQ.quantities.size()==0
    }
}
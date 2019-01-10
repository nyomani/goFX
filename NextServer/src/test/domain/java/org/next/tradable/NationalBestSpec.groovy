package org.next.tradable

import spock.lang.Specification

class NationalBestSpec extends Specification {

    def debit(p){
        Side.DEBIT.limit(p)
    }
    def credit(p){
        Side.CREDIT.limit(p)
    }
    def "test national best bid with on item"(){
        given:
            NationalBest best = new NationalBest();
        when:
            best.update('NYSE',debit(1.2),300)
        then:
            best.bestPrice==debit(1.2)
            best.bestMarket.quantities.size()==1
            best.bestMarket.quantities[0].exchange=='NYSE'
            best.bestMarket.quantities[0].quantity==300
    }
    def 'test best bid added first'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('NYSE',debit(1.2),300)
        best.update('CBOE',debit(1.1),400)
        best.update('BATS',debit(1.0),500)
        then:
        best.bestPrice==debit(1.2)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='NYSE'
        best.bestMarket.quantities[0].quantity==300

    }

    def 'test best bid added second'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('CBOE',debit(1.1),400)
        best.update('NYSE',debit(1.2),300)
        best.update('BATS',debit(1.0),500)
        then:
        best.bestPrice==debit(1.2)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='NYSE'
        best.bestMarket.quantities[0].quantity==300

    }
    def 'test best bid added last'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('CBOE',debit(1.1),400)
        best.update('BATS',debit(1.0),500)
        best.update('NYSE',debit(1.2),300)
        then:
        best.bestPrice==debit(1.2)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='NYSE'
        best.bestMarket.quantities[0].quantity==300

    }

    def "test national best ask with one item"(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('NYSE',credit(1.2),300)
        then:
        best.bestPrice==credit(1.2)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='NYSE'
        best.bestMarket.quantities[0].quantity==300
    }
    def 'test best ask added first'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('BATS',credit(1.0),500)
        best.update('NYSE',credit(1.2),300)
        best.update('CBOE',credit(1.1),400)
        then:
        best.bestPrice==credit(1.0)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='BATS'
        best.bestMarket.quantities[0].quantity==500

    }

    def 'test best ask added second'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('CBOE',credit(1.1),400)
        best.update('BATS',credit(1.0),500)
        best.update('NYSE',credit(1.2),300)
        then:
        best.bestPrice==credit(1.0)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='BATS'
        best.bestMarket.quantities[0].quantity==500

    }
    def 'test best ask added last'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('CBOE',credit(1.1),400)
        best.update('NYSE',credit(1.2),300)
        best.update('BATS',credit(1.0),500)
        then:
        best.bestPrice==credit(1.0)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='BATS'
        best.bestMarket.quantities[0].quantity==500

    }

    def 'test update best bid'(){
        given:
            NationalBest best = new NationalBest();
        when:
            best.update('NYSE',credit(1.2),300)
            best.update('NYSE',credit(1.1),500)
        then:
            best.bestPrice==credit(1.1)
            best.bestMarket.quantities.size()==1
            best.bestMarket.quantities[0].exchange=='NYSE'
            best.bestMarket.quantities[0].quantity==500

    }
    def 'test update best ask'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('NYSE',credit(1.2),300)
        best.update('NYSE',credit(1.1),500)
        then:
        best.bestPrice==credit(1.1)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='NYSE'
        best.bestMarket.quantities[0].quantity==500

    }

    def 'test update worse ask to be best ask'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('NYSE',credit(1.2),300)
        best.update('BATS',credit(1.1),500)
        best.update('CBOE',credit(1.3),500)
        best.update('CBOE',credit(1.0),500)

        then:
        best.bestPrice==credit(1.0)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='CBOE'
        best.bestMarket.quantities[0].quantity==500

    }

    def 'test update worse bid to be best bid'(){
        given:
        NationalBest best = new NationalBest();
        when:
        best.update('NYSE',debit(1.2),300)
        best.update('BATS',debit(1.1),500)
        best.update('CBOE',debit(1.3),500)
        best.update('BATS',debit(1.4),500)

        then:
        best.bestPrice==debit(1.4)
        best.bestMarket.quantities.size()==1
        best.bestMarket.quantities[0].exchange=='BATS'
        best.bestMarket.quantities[0].quantity==500

    }

}

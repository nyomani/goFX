package org.next.tradable

import spock.lang.Specification

class NationalBestBidOfferSpec extends Specification {

    def "test nbbo with one MC"(){
        given:
            NationalBestBidOffer nbbo = new NationalBestBidOffer()
        when:
            nbbo.update('NYSE',Side.DEBIT.limit(1.0),Side.CREDIT.limit(1.2),100,100)
        then:
            nbbo.bidPrice == Side.DEBIT.limit(1.0)
            nbbo.askPrice == Side.CREDIT.limit(1.2)
            nbbo.bestBids.quantities.size()==1
            nbbo.bestAsks.quantities.size()==1
    }
}
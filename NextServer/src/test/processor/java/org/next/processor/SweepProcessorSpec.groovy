package org.next.processor

import org.next.processor.order.SweepProcessor
import org.next.api.Book
import org.next.api.Instrument
import org.next.api.Intermarket
import org.next.api.IntermarketGateway
import org.next.api.IntermarketQuantities
import org.next.api.MatchAllocator
import org.next.tradable.Auction
import org.next.tradable.OrderInterest
import org.next.tradable.PriceListBookItem
import org.next.tradable.Side
import spock.lang.Specification


class SweepProcessorSpec extends Specification {
    SweepProcessor processor
    Auction        auction
    Book           book
    Intermarket    nbbo
    MatchAllocator  allocator
    IntermarketGateway intermarketGateway
    ArrayList<PriceListBookItem> bookItems
    ArrayList<IntermarketQuantities> intermarket
    OrderInterest order
    Instrument    instrument

    def credit(p){
        Side.CREDIT.limit(p)
    }
    def debit(p){
        Side.DEBIT.limit(p)
    }
    def book(prices){
        prices.forEach{p->
            PriceListBookItem items = Mock()
            items.getPrice() >> credit(p)
            bookItems.add(items)
        }
        book.getAsks(_,_) >> bookItems

    }
    def intermarket(prices){
        prices.forEach { p ->
            IntermarketQuantities items = Mock()
            items.getPrice() >> credit(p)
            intermarket.add(items)
        }
        nbbo.getAsks(_)>>intermarket
    }
    def setup(){
        auction = Mock()
        nbbo = Mock()
        allocator = Mock()
        intermarketGateway = Mock()
        processor = SweepProcessor.builder().allocator(allocator).intermarketGateway(intermarketGateway).build()
        bookItems=new ArrayList<>()
        intermarket = new ArrayList<>()
        instrument = Mock()
        book = Mock()
        instrument.getBook() >> book
        instrument.getAucktion()>>auction
        instrument.getNBO() >>nbbo
        order = Mock()
        auction.getOrder()>>order
        auction.getInstrument() >> instrument
        order.getAvailableQuantity()>>>[100,100,50,50,10,10,0]

    }

    def "When order only marketable internally"() {
        given:
            book ( [1.1,1.2,1.3])
            intermarket([1.3,1.4])
            auction.getPrice() >> debit(1.1)
            auction.getSide()>>Side.DEBIT

        when:
            processor.accept(auction)
        then:
            1 * allocator.allocate(auction.getOrder(),bookItems[0])
            1 * allocator.allocate(auction.getOrder(),bookItems[1])
            0 * intermarketGateway.accept(_,_,_)
    }

    def "When order only marketable at intermarket"() {
        given:
            book ( [1.4,1.5])
            intermarket([1.1,1.2,1.3])
            auction.getPrice() >> debit(1.1)
            auction.getSide()>>Side.DEBIT

        when:
            processor.accept(auction)
        then:
            0 * allocator.allocate(_,_)
            1 * intermarketGateway.accept(intermarket[0],order,_)
            1 * intermarketGateway.accept(intermarket[1],order,_)
    }

    def "When order marketable internally and then at intermarket"() {
        given:
            book ( [1.1,1.4])
            intermarket([1.2])
            auction.getPrice() >> debit(1.2)
            auction.getSide()>>Side.DEBIT

        when:
            processor.accept(auction)
        then:
            1 * allocator.allocate(order,bookItems[0])
        then:
            1 * intermarketGateway.accept(intermarket[0],order,50)
    }

    def "When order marketable at intermarket and then internally"() {
        given:
            book ( [1.2,1.4])
            intermarket([1.1])
            auction.getPrice() >> debit(1.2)
            auction.getSide()>>Side.DEBIT

        when:
            processor.accept(auction)
        then:
            1 * intermarketGateway.accept(intermarket[0],order,100)
        then:
            1 * allocator.allocate(order,bookItems[0])
    }
    def "When order marketable at intermarket and then internally then intermarket"() {
        given:
            book ( [1.2,1.4])
            intermarket([1.1,1.3])
            auction.getPrice() >> debit(1.3)
            auction.getSide()>>Side.DEBIT

        when:
            processor.accept(auction)
        then:
            1 * intermarketGateway.accept(intermarket[0],order,100)
        then:
            1 * allocator.allocate(order,bookItems[0])
        then:
            1 * intermarketGateway.accept(intermarket[1],order,10)
    }

}
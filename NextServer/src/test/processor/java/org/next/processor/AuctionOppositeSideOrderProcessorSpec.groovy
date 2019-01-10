package org.next.processor

import org.next.processor.order.AuctionOppositeSideOrderProcessor
import org.next.api.Book
import org.next.api.Instrument
import org.next.api.Price
import org.next.book.AuctionTerminateConsumer
import org.next.tradable.Auction
import org.next.tradable.AuctionTerminateReason
import org.next.tradable.OrderInterest
import org.next.tradable.Side
import spock.lang.Specification


class AuctionOppositeSideOrderProcessorSpec extends Specification {
    AuctionOppositeSideOrderProcessor orderProcessor
    AuctionTerminateConsumer<OrderInterest> terminator
    Instrument instrument
    Book book
    Auction auction
    OrderInterest order
    def setup(){
        terminator = Mock()
        instrument = Mock()
        book = Mock()
        auction = Mock()
        auction.getSide() >> Side.DEBIT
        order = Mock()
        instrument.getBook() >> book
        order.getInstrument() >> instrument
        orderProcessor = new AuctionOppositeSideOrderProcessor()
    }
    def debit(p){
        Side.DEBIT.limit(p)
    }
    def credit(p){
        Side.CREDIT.limit(p)
    }

    def "Order crossing auction should premature auction"(){
        given:
            order.getPrice() >> credit(1.05)
            auction.hasBetterPriceThan(order)>> true
            book.getBestBidPrice() >> debit(1.0)
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            1 * terminator.accept(auction,order, AuctionTerminateReason.PRICE_IMPROVED)
    }

    def "Order crossing the book should premature auction"(){
        given:
            order.getPrice() >> credit(1.0)
            auction.getPrice() >> debit(1.1)
            book.getBestBidPrice() >> debit(1.0)
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            1 * terminator.accept(auction,order, AuctionTerminateReason.INCOMING_CROSSED_BOOK)
    }
    def "Market Order should premature auction"(){
        given:
        order.getPrice() >> Price.sellAtMarket()
        auction.getPrice() >> debit(1.1)
        book.getBestBidPrice() >> debit(1.0)
        when:
        orderProcessor.accept(auction,order,terminator)
        then:
        1 * terminator.accept(auction,order, AuctionTerminateReason.INCOMING_CROSSED_BOOK)
    }
}
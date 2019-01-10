package org.next.processor

import org.next.processor.order.AuctionSameSideOrderProcessor
import org.next.api.Instrument
import org.next.api.Price
import org.next.book.AuctionTerminateConsumer
import org.next.tradable.Auction
import org.next.tradable.AuctionTerminateReason
import org.next.tradable.OrderInterest
import org.next.tradable.Side
import spock.lang.Specification

class AuctionSameSideOrderProcessorSpec extends Specification {
    AuctionSameSideOrderProcessor orderProcessor
    AuctionTerminateConsumer<OrderInterest> terminator
    Auction auction
    Instrument instrument
    OrderInterest order
    def debit(p){
        Side.DEBIT.limit(p)
    }
    def credit(p){
        Side.CREDIT.limit(p)
    }

    def setup(){
        order = Mock()
        auction = Mock()
        instrument = Mock()
        terminator = Mock()
        orderProcessor = new AuctionSameSideOrderProcessor()
    }

    def "Incoming order with better price should premature auction"() {
        given:
            auction.getSide() >> Side.DEBIT
            auction.getPrice() >> debit(1.0)
            order.getPrice() >> debit(1.1)
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            1 * terminator.accept(auction,order, AuctionTerminateReason.SAME_SIDE_BETTER_PRICE)
    }
    def "Incoming market order should premature auction"() {
        given:
            auction.getSide() >> Side.DEBIT
            auction.getPrice() >> debit(1.0)
            order.getPrice() >> Price.bidAtMarket()
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            1 * terminator.accept(auction,order, AuctionTerminateReason.SAME_SIDE_BETTER_PRICE)
    }

    def "Incoming order with same price should partake int the auction"() {
        given:
            auction.getSide() >> Side.DEBIT
            auction.getPrice() >> debit(1.0)
            order.getPrice() >> debit(1.0)
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            1 * auction.partake(order)
    }
    def "Incoming order with worse price should neither partake nor premature  the auction"() {
        given:
            auction.getSide() >> Side.DEBIT
            auction.getPrice() >> debit(1.0)
            order.getPrice() >> debit(0.9)
        when:
            orderProcessor.accept(auction,order,terminator)
        then:
            0 * auction.partake(order)
            0 * terminator.accept(_,_,_)
    }
}
package org.next.processor

import org.next.processor.order.ActiveAuctionOrderProcessor
import org.next.api.Book
import org.next.api.Instrument
import org.next.book.ActiveAuctionInterestProcessor
import org.next.tradable.Auction
import org.next.tradable.AuctionTerminateReason
import org.next.tradable.OrderInterest
import org.next.tradable.Side
import spock.lang.Specification

import java.util.function.Consumer


class ActiveAuctionOrderProcessorSpec extends Specification {
     ActiveAuctionInterestProcessor<OrderInterest> sameSideProcessor;
     ActiveAuctionInterestProcessor<OrderInterest> oppositeSideProcessor;
     Consumer<Auction> sweepProcessor;
     OrderInterest                                 order
     ActiveAuctionOrderProcessor                   orderProcessor
     Instrument                                    instrument
    Book                                           book;

    def setup(){
        sameSideProcessor = Mock()
        oppositeSideProcessor =Mock()
        sweepProcessor = Mock()
        order = Mock()
        book = Mock()
        instrument = Mock()
        instrument.getBook() >> book
        order.getInstrument() >> instrument
        orderProcessor = ActiveAuctionOrderProcessor.builder()
                        .sameSideProcessor(sameSideProcessor )
                        .oppositeSideProcessor(oppositeSideProcessor)
                        .sweepProcessor(sweepProcessor).build();
    }


    def "when there is no active auction exist"() {
        given:
            instrument.isAuctioning() >> false
        when:
            orderProcessor.accept(order)
        then:
            0 * sameSideProcessor.accept(_,_,_)
            0 * oppositeSideProcessor.accept(_,_,_)
    }
    def "when same side auction exist"() {
        given:
            Auction auction = Mock()
            auction.getInstrument() >>instrument
            auction.getSide()>> Side.DEBIT
            order.getSide() >> Side.DEBIT
            instrument.isAuctioning() >> true
            instrument.getAucktion() >> auction
        when:
            orderProcessor.accept(order)
        then:
            1 * sameSideProcessor.accept(_,_,_)
            0 * oppositeSideProcessor.accept(_,_,_)
    }
    def "when opposite side auction exist"() {
        given:
            Auction auction = Mock()
            auction.getInstrument() >>instrument
            auction.getSide()>> Side.CREDIT
            order.getSide() >> Side.DEBIT
            instrument.isAuctioning() >> true
            instrument.getAucktion() >> auction
        when:
            orderProcessor.accept(order)
        then:
            0 * sameSideProcessor.accept(_,_,_)
            1 * oppositeSideProcessor.accept(_,_,_)
    }

    def "when same side order at better price prematured active auction"(){
        given:
            Auction auction = Mock()
            auction.getInstrument() >>instrument
            OrderInterest auctionedOrder = Mock()
            auction.getOrder() >> auctionedOrder
            auction.getSide()>> Side.DEBIT
            order.getSide() >> Side.DEBIT
            instrument.isAuctioning() >> true
            instrument.getAucktion() >> auction
            sameSideProcessor.accept(auction,order,_) >>{args->
                args[2].accept(auction,order, AuctionTerminateReason.SAME_SIDE_BETTER_PRICE)
            }
        when:
            orderProcessor.accept(order)
        then:
            1 * auction.terminate(AuctionTerminateReason.SAME_SIDE_BETTER_PRICE)
            1 * sweepProcessor.accept(auction)
            1 * auctionedOrder.book()
            0 * order.book()
    }
    def "when opposite side order at better price prematured active auction"(){
        given:
            Auction auction = Mock()
            auction.getInstrument() >>instrument
            OrderInterest auctionedOrder = Mock()
            auction.getOrder() >> auctionedOrder
            auction.getSide()>> Side.CREDIT
            order.getSide() >> Side.DEBIT
            instrument.isAuctioning() >> true
            instrument.getAucktion() >> auction
            oppositeSideProcessor.accept(auction,order,_) >>{args->
                args[2].accept(auction,order, AuctionTerminateReason.INCOMING_CROSSED_BOOK)
            }
        when:
            orderProcessor.accept(order)
        then:
            1 * auction.terminate(AuctionTerminateReason.INCOMING_CROSSED_BOOK)
            1 * sweepProcessor.accept(auction)
            1 * auctionedOrder.book()
            1 * order.book()
    }
}
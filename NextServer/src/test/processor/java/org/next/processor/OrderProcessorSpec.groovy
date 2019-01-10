package org.next.processor


import org.next.processor.order.OrderProcessor
import org.nex.tradable.Order
import org.next.api.Book
import org.next.api.Instrument
import org.next.api.Intermarket
import org.next.api.UserSession
import org.next.tradable.Interest
import org.next.tradable.OrderInterest
import org.next.tradable.Side
import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Predicate

class OrderProcessorSpec extends Specification {
     Predicate<OrderInterest> validationProcessor;
     Consumer<OrderInterest> activeAuctionOrderProcessor;
     Consumer<Interest> sweepBookProcessor;
     Consumer<Interest> auctionProcessor;
     Consumer<Interest> reminderProcessor;
    Intermarket nbbo = Mock()
    Book        book = Mock()
    Instrument  instrument = Mock()
    UserSession session = Mock()
    OrderProcessor orderProcessor
    Order         order

    def setup(){
        validationProcessor = Mock()
        activeAuctionOrderProcessor=Mock()
        sweepBookProcessor=Mock()
        auctionProcessor=Mock()
        reminderProcessor=Mock()
        validationProcessor=Mock()
        nbbo = Mock()
        book = Mock()
        instrument = Mock()
        instrument.getBook() >> book
        instrument.getNBO() >> nbbo
        orderProcessor = OrderProcessor.builder()
                .validationProcessor(validationProcessor)
                .activeAuctionOrderProcessor(activeAuctionOrderProcessor)
                .sweepBookProcessor(sweepBookProcessor)
                .auctionProcessor(auctionProcessor)
                .reminderProcessor(reminderProcessor).build()
        order = new Order(session,instrument);
        order.price = Side.DEBIT.limit(1.0)
    }

    def "When order is not valid"(){
        given:
        validationProcessor.test(order) >> false
        when:
        orderProcessor.accept(order)
        then:
        0 * instrument.lock()
        0 * activeAuctionOrderProcessor.accept(_)
        0 * sweepBookProcessor.accept(_)
        0 * auctionProcessor.accept(_)
        0 * instrument.release()
        0 * reminderProcessor.accept(_)
    }
    def "When order is  valid"(){
        given:
        validationProcessor.test(order) >>true
        when:
        orderProcessor.accept(order)
        then:
        1 * instrument.lock()
        1 * activeAuctionOrderProcessor.accept(_)
        1 * sweepBookProcessor.accept(_)
        1 * auctionProcessor.accept(_)
        1 * instrument.release()
        1 * reminderProcessor.accept(_)
    }
}
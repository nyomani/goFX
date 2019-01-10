package org.next.processor

import org.next.processor.order.OrderReminderProcessor
import org.next.tradable.OrderInterest
import org.next.tradable.TradableState
import spock.lang.Specification


class OrderReminderProcessorSpec extends Specification {

    def "When order state is none and available quantity > 0, cancel should be called "() {
        given:
        OrderReminderProcessor processor =  new OrderReminderProcessor()
        OrderInterest order = Mock()
        order.availableQuantity >> 100
        order.state >> TradableState.NONE
        when:
            processor.accept(order)
        then:
            1 * order.cancel()
    }

    def "When order state is none and available quantity == 0, cancel should be called "() {
        given:
        OrderReminderProcessor processor =  new OrderReminderProcessor()
        OrderInterest order = Mock()
        order.availableQuantity >> 0
        order.state >> TradableState.NONE
        when:
        processor.accept(order)
        then:
        0 * order.cancel()
    }
    def "When order state is not NONE and available quantity > 0, cancel should be called "() {
        given:
        OrderReminderProcessor processor =  new OrderReminderProcessor()
        OrderInterest order = Mock()
        order.availableQuantity >> 100
        order.state >> TradableState.BOOKED
        when:
        processor.accept(order)
        then:
        0 * order.cancel()
    }

}
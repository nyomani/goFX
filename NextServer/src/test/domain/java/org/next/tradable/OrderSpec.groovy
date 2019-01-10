package org.next.tradable

import org.nex.tradable.Order
import org.next.api.Book
import org.next.api.Instrument
import org.next.api.UserSession
import spock.lang.Specification

class OrderSpec extends Specification {
    Order order
    Book book
    def debit(p){
        Side.DEBIT.limit(p)
    }
    def setup(){
        UserSession session = Mock()
        Instrument instrument = Mock()
        book = Mock()
        instrument.getBook()>>book;
        order = new Order(session,instrument)
    }
    def "verify order "(){
        when:
           order.price = debit(1.2)
        then:
           order.price == debit(1.2)
        when:
           order.priority = Priority.CUSTOMER
        then:
           order.priority == Priority.CUSTOMER
        when:
           order.originalQuantity=1000
        then:
           order.originalQuantity==1000
           order.displayQuantity==1000
           order.availableQuantity==1000

       }

    def "test fill"(){
        given:
            order.price = debit(1.2)
            order.originalQuantity=1000
        when:
            order.fill(debit(1.2),100)
        then:
            order.originalQuantity==1000
            order.displayQuantity==900
            order.availableQuantity==900
            order.tradedQuantity == 100
    }

    def "test cancel"(){
        given:
            order.price = debit(1.2)
            order.originalQuantity=1000
        when:
            order.cancel()
        then:
            order.originalQuantity==1000
            order.displayQuantity==0
            order.availableQuantity==0
            order.tradedQuantity == 0
            order.canceledQuantity==1000
    }

    def "test update"(){
        given:
            order.price = debit(1.2)
            order.originalQuantity=1000
        when:
            order.update(debit(1.2),900)
        then:
            order.originalQuantity==1000
            order.displayQuantity==900
            order.availableQuantity==900
            order.tradedQuantity == 0
            order.canceledQuantity==100

    }

    def "test booking"(){
        given:
            order.price = debit(1.2)
            order.originalQuantity=1000
        when:
            order.book()
        then:
            1 * book.book(order,debit(1.2),1000)

    }
    def "test updating order"() {
        given:
            order.price = debit(1.2)
            order.originalQuantity = 1000
        when:
            order.book()
            order.update(debit(1.1),300)
        then:
            1 * book.book(_,_,_)
    }
}

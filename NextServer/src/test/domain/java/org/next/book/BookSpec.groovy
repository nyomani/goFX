package org.next.book

import org.next.tradable.BookItem
import org.next.tradable.Interest
import org.next.tradable.Side
import spock.lang.Specification

public class BookSpec extends Specification {
   TradableBook book

    def debit(p){
        Side.DEBIT.limit(p)
    }
    def credit(p){
        Side.CREDIT.limit(p)
    }
    def setup(){
        book  = new TradableBook();
    }

    def "Book a tradable"(){
        given:
            Interest t = Mock()
        when:
            BookItem item = book.book(t,debit(1.0),100)
        then:
            item.getInterest() == t

    }
    def "Test Book best bid price with single price"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.DEBIT
        when:
            book.book(t,debit(1.0),100)
        then:
            book.getBestBidPrice() == debit(1.0)
    }
    def "Test Book best bid price with multiple prices"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.DEBIT
            Interest t1 = Mock()
            t1.getSide() >> Side.DEBIT
        when:
            book.book(t,debit(1.0),100)
            book.book(t,debit(1.1),100)
        then:
            book.getBestBidPrice() == debit(1.1)
    }

    def "Test Book best ask price with single price"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.CREDIT
        when:
            book.book(t,credit(1.0),100)
        then:
            book.getBestAskPrice()== credit(1.0)
    }

    def "Test Book best ask price with multiple prices"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.CREDIT
            Interest t1 = Mock()
            t1.getSide() >> Side.CREDIT
        when:
            book.book(t,credit(1.0),100)
            book.book(t,credit(1.1),100)
        then:
            book.getBestAskPrice() == credit(1.0)
    }
    def "Cancel sell tradable"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.CREDIT
        when:
            BookItem bi = book.book(t,credit(1.0),100)
            bi.remove()
        then:
            book.getBestAskPrice()== Side.CREDIT.worsePrice;

    }

    def "Update sell tradable"(){
        given:
            Interest t = Mock()
            t.getSide() >> Side.CREDIT
        when:
            BookItem bi = book.book(t,credit(1.0),100)
            bi.update(credit(1.15),10);
        then:
            book.getBestAskPrice()== credit(1.15);

    }
}

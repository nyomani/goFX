package org.next.book

import org.next.api.Price
import org.next.tradable.BookItem
import org.next.tradable.Interest
import org.next.tradable.Priority
import org.next.tradable.Side
import spock.lang.Specification

class BookSideSpec extends Specification {

    def debit={def p->
        Side.DEBIT.limit(p)
    }

    def credit={def p->
        Side.CREDIT.limit(p)
    }
    def "booking a single entry should returns single price"(){
        given:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t = Mock()
        when:
        book.add(t,debit(1.0),20)
        then:
        book.getPrices().size() == 1
    }

    def "booking multiple entries with same price should returns single price"(){
        given:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        Interest t2 = Mock()
        when:
        book.add(t1,debit(1.0),20)
        book.add(t2,debit(1.0),20)
        then:
        book.getPrices().size() == 1
    }

    def "booking multiple entries with different price should returns multiple prices"(){
        given:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        Interest t2 = Mock()
        when:
        book.add(t1,debit(1.0),20)
        book.add(t2,debit(1.1),20)
        then:
        book.getPrices().size() == 2
    }

    def "after the only entry removed prices should be empty"(){
        given:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        when:
        BookItem item= book.add(t1,debit(1.0),20)
        then:
        book.getPrices().size() == 1
        when:
        item.remove()
        then:
        book.getPrices().size() == 0

    }

    def "after the only entry price updated prices should be 1"(){
        given:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        when:
        BookItem item= book.add(t1,debit(1.0),20)
        then:
        book.getPrices().size() == 1
        when:
        item.update(debit(1.1),20)
        then:
        book.getPrices().size() == 1
        book.getBestPrice()==debit(1.1)

    }

    def "test empty bid side best"(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        then:
            book.getBestPrice() == Side.DEBIT.worsePrice;
    }
    def "test empty bid side get tradables at a price"(){
        when:
            BookSide book = new BookSide(Side.DEBIT)
        then:
            book.getTradablesAtPrice(debit(1.0),{true})== TradableIterator.EMPTY_ITERATOR;
    }
    def "test bid side get tradables at a price that does not exist should return empty iterator "(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t = Mock()
        book.add(t,debit(1.0),20)
        then:
        book.getTradablesAtPrice(debit(1.1),{true})== TradableIterator.EMPTY_ITERATOR;
    }
    def "test bid side get tradables at a price that does exist should not return empty iterator "(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t = Mock()
        book.add(t,debit(1.0),20)
        then:
        book.getTradablesAtPrice(debit(1.0),{true}) != TradableIterator.EMPTY_ITERATOR;
    }

    def "bid side best price should return the highest price"(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        Interest t2 = Mock()
        Interest t3 = Mock()
        book.add(t1,debit(1.0),20)
        book.add(t2,debit(0.9),20)
        book.add(t3,debit(0.95),20)
        then:
        book.getBestPrice() == debit(1.0)
    }
    def "ask side best price should return the lowest price"(){
        when:
        BookSide book = new BookSide(Side.CREDIT)
        Interest t1 = Mock()
        Interest t2 = Mock()
        Interest t3 = Mock()
        book.add(t1,credit(1.0),20)
        book.add(t2,credit(0.9),20)
        book.add(t3,credit(0.95),20)
        then:
        book.getBestPrice() == debit(0.9)
    }

    def "test get all tradables at price higher than 9000 should return 2 prices"(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        t1.priority>> Priority.BROKER
        Interest t2 = Mock()
        t2.priority >>Priority.CUSTOMER
        Interest t3 = Mock()
        t3.priority >>Priority.CUSTOMER
        book.add(t1,debit(1.1),20)
        book.add(t2,debit(1.0),20)
        book.add(t3,debit(0.9),20)

        List<TradableIterator<BookItem>> iteratorList = book.getTradables(
                {it.betterThan(debit(0.9))},
                {true})
        then:
        iteratorList.size() == 2
    }

    def "test get all bid side BROKER trables at price higher than 9000 should return 1 prices"(){
        when:
        BookSide book = new BookSide(Side.DEBIT)
        Interest t1 = Mock()
        t1.priority>> Priority.BROKER
        Interest t2 = Mock()
        t2.priority >>Priority.CUSTOMER
        Interest t3 = Mock()
        t3.priority >>Priority.CUSTOMER
        book.add(t1,debit(1.1),20)
        book.add(t2,debit(1.0),20)
        book.add(t3,debit(0.9),20)

        List<TradableIterator<BookItem>> iteratorList = book.getTradables({it.betterThan(debit(0.9))},{it.getInterest().priority==Priority.BROKER})
        then:
        iteratorList.size() == 1
    }
    def "test get all sell side BROKER trables at price less than 11000 should return 1 prices"(){
        when:
            BookSide book = new BookSide(Side.CREDIT)
            Interest t1 = Mock()
            t1.priority>> Priority.BROKER
            Interest t2 = Mock()
            t2.priority >>Priority.BROKER
            Interest t3 = Mock()
            t3.priority >>Priority.CUSTOMER
            book.add(t3,credit(0.9),20)
            book.add(t1,credit(1.1),20)
            book.add(t2,credit(1.0),20)

            List<TradableIterator<BookItem>> iteratorList = book.getTradables({it.betterThan(credit(1.1))},{it.getInterest().priority==Priority.BROKER})
        then:
            iteratorList.size() == 1
    }

    def "Sell side price should be order in ascending mode"() {
        given:
        BookSide book = new BookSide(Side.CREDIT)
        Interest t1 = Mock()
        Interest t2 = Mock()
        Interest t3 = Mock()
        book.add(t3,credit(0.9),20)
        book.add(t1,credit(1.1),20)
        book.add(t2,credit(1.0),20)

        when:
        Price[] prices = book.getPrices().toArray();
        then:
        prices[0] == credit(0.9)
        prices[1] == credit(1.0)
        prices[2] == credit(1.1)
    }
    def "Buy side price should be order in descending mode"() {
        given:
            BookSide book = new BookSide(Side.DEBIT)
            Interest t1 = Mock()
            Interest t2 = Mock()
            Interest t3 = Mock()
            book.add(t3,debit(0.9),20)
            book.add(t1,debit(1.1),20)
            book.add(t2,debit(1.0),20)

        when:
            Price[] prices = book.getPrices().toArray();
        then:
            prices[0] == debit(1.1)
            prices[1] == debit(1.0)
            prices[2] == debit(0.9)
    }
}
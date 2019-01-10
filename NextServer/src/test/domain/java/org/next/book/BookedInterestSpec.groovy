package org.next.book

import org.next.tradable.Interest
import org.next.tradable.Side
import org.next.util.LinkList
import spock.lang.Specification

class BookedInterestSpec extends Specification {

    def debit(p){
        Side.DEBIT.limit(p)
    }
    def "verify booked quantity and price"(){
        when:
        Interest t = Mock()
        t.id >> 1234
        BookedTradable bt = new BookedTradable(t,debit(1.0),20,{bt})
        then:
        bt.bookedPrice == debit(1.0)
        bt.bookedQuantity == 20
        bt.itemKey ==1234
    }

    def "verify add to list"(){
        when:
        Interest t = Mock()
        BookedTradable bt = new BookedTradable(t,debit(1.0),20,{bt})
        LinkList<BookedTradable> list = new LinkList<>({})
        bt.linkTo(list);
        then:
        list.root.value() == bt

    }

    def "verify removed from list"(){
        when:
        Interest t = Mock()
        BookedTradable bt = new BookedTradable(t,debit(1.0),20,{bt})
        LinkList<BookedTradable> list = new LinkList<>({})
        bt.linkTo(list);
        then:
        list.root.value() == bt
        when:
        bt.remove()
        then:
        list.root==null

    }

}
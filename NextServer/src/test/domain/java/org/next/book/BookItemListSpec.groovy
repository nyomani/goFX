package org.next.book

import org.next.tradable.BookItem
import org.next.tradable.Interest
import org.next.tradable.Side
import org.next.tradable.SideBookItem
import spock.lang.Specification

class BookItemListSpec extends Specification {
    def debit={def p->
        Side.DEBIT.limit(p)
    }
    def " get price should return same price as the price passed on"(){
        when:
        SideBookItem sideBook = Mock()
        BookItemList list = new BookItemList(debit(1.0),sideBook);
        then:
        list.price == debit(1.0)

    }
    def "when item is added, iterator should not empty"(){
        when:
        SideBookItem sideBook = Mock()
        Interest t = Mock()
        BookItemList list = new BookItemList(debit(1.0),sideBook);
        list.add(t,23)
        then:
        list.iterator({true}).hasNext() == true
    }


    def "when item is removed, iterator should be empty"(){
        when:
        SideBookItem sideBook = Mock()
        Interest t = Mock()
        BookItemList list = new BookItemList(debit(1.0),sideBook);
        BookItem item =list.add(t,23)
        then:
        list.iterator({true}).hasNext() == true
        when:
        item.remove()
        then:
        list.iterator({true}).hasNext() == false
    }
}
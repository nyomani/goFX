package org.next.book

import org.next.tradable.BookItem
import org.next.tradable.Side
import org.next.util.LinkList
import org.next.util.ListNode
import spock.lang.Specification

class InterestIteratorSpec extends Specification {

    def "Test empty iterator has next should returns false"(){
        when:
            TradableIterator iter = TradableIterator.EMPTY_ITERATOR;
        then:
            iter.hasNext() == false

    }

    def "Test empty iterator  next should returns null"(){
        when:
            TradableIterator iter = TradableIterator.EMPTY_ITERATOR;
        then:
            iter.next() == null
    }

    def "Test single entry  iterator next should return value"(){
        when:
            BookItem head = Mock()
            TradableIterator iter = new TradableIterator(new ListNode(head))
        then:
            iter.next() == head
    }
    def "Test single entry  hasNext should return true"() {
        when:
        BookItem head = Mock()
        TradableIterator iter = new TradableIterator(new ListNode(head))
        then:
        iter.hasNext() == true
    }

    def "Test multiple entries  iterator next should return value"(){
        when:
        BookItem item1 = Mock()
        BookItem item2 = Mock()
        LinkList<BookItem> ll = new LinkList<>();

        ListNode node = new ListNode(item1)
        node.linkTo(ll)
        node = new ListNode(item2)
        node.linkTo(ll)
        TradableIterator iter = new TradableIterator(ll.root)
        then:
        iter.next() == item1
        iter.next() == item2
        iter.next() == null
    }

    def "Test multiple entries  hasNext should return true"() {
        when:
        BookItem item1 = Mock()

        BookItem item2 = Mock()
        LinkList<BookItem> ll = new LinkList<>();

        ListNode node = new ListNode(item1)
        node.linkTo(ll)
        node = new ListNode(item2)
        node.linkTo(ll)
        TradableIterator iter = new TradableIterator(ll.root)
        then:
        iter.hasNext() == true
        when:
        iter.next()
        then:
        iter.hasNext() == true
        when:
        iter.next()
        then:
        iter.hasNext() == false
    }

    def "Test multiple entries with filter match one item"() {
        when:
        BookItem item1 = Mock()

        BookItem item2 = Mock()
        item2.getBookedPrice() >> Side.DEBIT.limit(1.0)
        LinkList<BookItem> ll = new LinkList<>();

        ListNode node = new ListNode(item1)
        node.linkTo(ll)
        node = new ListNode(item2)
        node.linkTo(ll)

        TradableIterator iter = new TradableIterator(ll.root,{it.getBookedPrice()==Side.DEBIT.limit(1.0)})
        then:
        iter.hasNext() == true
        iter.next() == item2
        then:
        iter.hasNext() == false
        iter.next() == null
    }

}
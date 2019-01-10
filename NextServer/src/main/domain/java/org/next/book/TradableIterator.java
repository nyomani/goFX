package org.next.book;

import org.next.api.Price;
import org.next.tradable.*;
import org.next.util.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TradableIterator<T extends BookItem> implements PriceListBookItem {
    public static final Predicate<BookItem> ALL = e->true;

    public static final TradableIterator EMPTY_ITERATOR = new TradableIterator();
    private Node<T> current;
    private Node<T> head;
    private Predicate<T> filter;
    private Price price;
    private TradableIterator() {
        current = null;
        head =null;
        filter = e->false;

    }
    public TradableIterator(Node<T> head){

        current = head;
        filter = e->true;
        this.head = current;
        if(head !=null)
            price = head.value().getBookedPrice();
    }

    public TradableIterator(Node<T> head, Predicate<T> filter){
        current = head;
        if(head !=null)
            price = head.value().getBookedPrice();
        this.filter=filter;
        this.head = current;
        setNext();
    }


    @Override
    public boolean hasNext() {
        return current !=null;
    }

    public T next(){
        Node<T> returnValue = current;
        if(current !=null)
            current = current.getNext();
        setNext();

        return returnValue==null?null:returnValue.value();
    }

    private void setNext(){
        while (current !=null && !filter.test(current.value()))
            current = current.getNext();

    }

    public List<T> asList(){
        List<T> list = new ArrayList<>();
        current = head;
        while (hasNext()){
            list.add(next());
        }
        current = head;
        return list;
    }

    public Price getPrice() {
        return  price;
    }

}

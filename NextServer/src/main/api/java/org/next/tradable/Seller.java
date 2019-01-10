package org.next.tradable;

import org.next.api.Book;
import org.next.api.Intermarket;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.Iterator;
import java.util.function.Predicate;

public class Seller implements Sider {
    @Override
    public Price getBestPrice(Book book) {
        return book.getBestAskPrice();
    }

    @Override
    public Price getBestPrice(Intermarket market) {
        return market.getAskPrice();
    }

    @Override
    public int comparePrice(Price p1, Price p2) {
        return comparator.compare(p1,p2);
    }

    @Override
    public Iterator<PriceListBookItem> getTradables(Book book, Price priceConstraints, Predicate<BookItem> filter) {
        return book.getAsks(price -> price.compareTo(priceConstraints)<=0, filter).iterator();
    }

    @Override
    public Side opposite() {
        return Side.DEBIT;
    }

    @Override
    public Iterator<IntermarketQuantities> getNationMarkets(Intermarket market, Predicate<Price> filter) {
        return market.getAsks(filter).iterator();
    }

}


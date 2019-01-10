package org.next.tradable;

import org.next.api.Book;
import org.next.api.Intermarket;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.Iterator;
import java.util.function.Predicate;

public class Buyer implements Sider {
    @Override
    public Price getBestPrice(Book book) {
        return book.getBestBidPrice();
    }

    @Override
    public Price getBestPrice(Intermarket market) {
        return market.getBidPrice();
    }

    @Override
    public Iterator<PriceListBookItem> getTradables(Book book, Price priceConstraints, Predicate<BookItem> filter) {
        return book.getBids(price -> price.betterOrEqual(priceConstraints), filter).iterator();
    }

    @Override
    public int comparePrice(Price p1, Price p2) {
        return -1 * comparator.compare(p1,p2);
    }

    @Override
    public Side opposite() {
        return Side.CREDIT;
    }

    @Override
    public Iterator<IntermarketQuantities> getNationMarkets(Intermarket market, Predicate<Price> filter) {
        return market.getBids(filter).iterator();
    }

}


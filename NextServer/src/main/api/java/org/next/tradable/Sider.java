package org.next.tradable;

import org.next.api.Book;
import org.next.api.Intermarket;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.Iterator;
import java.util.function.Predicate;

public interface Sider {
    PriceComparator comparator = new PriceComparator();
    Price getBestPrice(Book book);
    Price getBestPrice(Intermarket market);
    int   comparePrice(Price p1,Price p2);
    default boolean betterPrice(Price p1, Price p2) {
        return comparePrice(p1,p2) <0;
    }
    default boolean betterOrEqual(Price p1,Price p2){
        return p1.equals(p2) || betterPrice(p1,p2);
    }

    default Price getWorsePrice() {
        return Price.none();
    }

    Side opposite();
    Iterator<PriceListBookItem> getTradables(Book book, Price priceConstraints, Predicate<BookItem> filter);
    Iterator<IntermarketQuantities> getNationMarkets(Intermarket market, Predicate<Price> filter);
}

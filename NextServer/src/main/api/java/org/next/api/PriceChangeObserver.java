package org.next.api;

import org.next.tradable.BookItem;

public interface PriceChangeObserver {
    BookItem priceUpdated(BookItem t, Price newPrice,int newQuantity);
}

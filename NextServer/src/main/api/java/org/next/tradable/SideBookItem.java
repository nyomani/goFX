package org.next.tradable;

import org.next.api.Price;
import org.next.api.PriceChangeObserver;

public interface SideBookItem extends PriceChangeObserver {

    void remove(Price price);
    BookItem add(Interest interest, Price price, int quantity);
}

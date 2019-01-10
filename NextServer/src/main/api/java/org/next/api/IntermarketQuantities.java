package org.next.api;

import java.util.Collection;

public interface IntermarketQuantities {
    Collection<MarketCenter> getQuantities();
    void update(String excahnge,Price price,int quantity);
    Price getPrice();
    void remove(String exchange);
}

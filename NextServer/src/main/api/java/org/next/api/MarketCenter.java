package org.next.api;

public interface MarketCenter {
    String getExchange();

    int getQuantity();

    Price getPrice();

    void update(String exch, Price price, int qty);
}
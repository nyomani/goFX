package org.next.api;

import org.next.tradable.OrderInterest;

public interface IntermarketGateway {
    void accept(IntermarketQuantities markets, OrderInterest order, int quantity);
}

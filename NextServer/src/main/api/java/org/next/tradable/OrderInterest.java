package org.next.tradable;

import org.next.api.IntermarketQuantities;

import java.util.function.Consumer;

public interface OrderInterest extends Interest {
    int getMinimumQuantityToTrade();
    int getDisplayQuantity();
    TradableState getState();
    Auction createAuction();
    void route(IntermarketQuantities mc, Consumer<IntermarketInterest> gateway);
    void routeCancel(int quantity);
}

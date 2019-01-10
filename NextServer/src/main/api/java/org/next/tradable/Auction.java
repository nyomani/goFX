package org.next.tradable;

import org.next.api.Price;

import java.util.List;

public interface Auction extends TradeItem{
    long   getAuctionId();
    OrderInterest getOrder();
    List<BookItem> getAuctionResponses();
    void terminate(AuctionTerminateReason reason);
    void start(int duration);
    void partake(OrderInterest order);
    AuctionState getState();
    int  getAvailableQuantity();
}

package org.next.api;

import org.next.tradable.Auction;

public interface Instrument {
    long           getId();
    String         getSymbol();
    InstrumentType getType();
    AssetClass     getAssetClass();
    Book           getBook();
    Auction        getAucktion();
    Intermarket    getNBO();
    TradingState   getState();
    void           setTradingState(TradingState state);
    void           auctionStarted(Auction a);
    void           auctionTerminated();
    boolean        isAuctioning();
    void           lock();


    void release();
}

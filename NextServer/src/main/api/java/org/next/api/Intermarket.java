package org.next.api;

import org.next.tradable.SweepParticipant;

import java.util.Collection;
import java.util.function.Predicate;

public interface Intermarket {

    Price getBidPrice();
    Price getAskPrice();
    IntermarketQuantities getBestBids();
    IntermarketQuantities getBestAsks();
    Collection<IntermarketQuantities> getBids(Predicate<Price> filter);
    Collection<IntermarketQuantities> getAsks(Predicate<Price> filter);

    void update(String exchange, Price bidPrice, Price askPrice, int bidQty, int askQty);
}

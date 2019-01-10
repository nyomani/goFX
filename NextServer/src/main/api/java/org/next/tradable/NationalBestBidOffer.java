package org.next.tradable;

import org.next.api.Intermarket;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.Collection;
import java.util.function.Predicate;

public class NationalBestBidOffer implements Intermarket {
    private NationalBest bid = new NationalBest();
    private NationalBest ask = new NationalBest();

    @Override
    public Price getBidPrice() {
        return bid.getBestPrice();
    }

    @Override
    public Price getAskPrice() {
        return ask.getBestPrice();
    }

    @Override
    public IntermarketQuantities getBestBids() {
        return bid.getBestMarket();
    }

    @Override
    public IntermarketQuantities getBestAsks() {
        return ask.getBestMarket();
    }

    @Override
    public Collection<IntermarketQuantities> getBids(Predicate<Price> filter) {
        return null;
    }

    @Override
    public Collection<IntermarketQuantities> getAsks(Predicate<Price> filter) {
        return null;
    }

    @Override
    public void update(String exchange, Price bidPrice, Price askPrice, int bidQty, int askQty){
        bid.update(exchange,bidPrice,bidQty);
        ask.update(exchange,askPrice,askQty);
    }

}

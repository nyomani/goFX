package org.next.tradable;

import org.next.api.IntermarketQuantities;
import org.next.api.MarketCenter;
import org.next.api.Price;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MarketCenterQuantities implements IntermarketQuantities {
    Map<String,MarketCenter> mcMap = new HashMap<>();
    Price price;

    public MarketCenterQuantities(Price price){
        this.price = price;
    }
    @Override
    public Collection<MarketCenter> getQuantities() {
        return mcMap.values();
    }

    @Override
    public void update(String exchange, Price price, int quantity) {
        if (quantity == 0){
            remove(exchange);
            return;
        }

        MarketCenter mc = mcMap.get(exchange);
        if (mc == null){
            mc = new MarketCenterBest();
            mcMap.put(exchange,mc);
        }
        mc.update(exchange,price,quantity);
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public void remove(String exchange) {
        mcMap.remove(exchange);
    }
}

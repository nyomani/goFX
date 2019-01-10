package org.next.tradable;

import lombok.Data;
import org.next.api.MarketCenter;
import org.next.api.Price;

@Data
public class MarketCenterBest implements MarketCenter {
    String exchange;
    Price price;
    int quantity;

    @Override
    public void update(String exch,Price price,int qty){
        this.exchange = exch;
        this.price=price;
        this.quantity = qty;
    }

}

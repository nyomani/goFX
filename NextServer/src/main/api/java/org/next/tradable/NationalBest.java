package org.next.tradable;

import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.*;
import java.util.function.Predicate;

public class NationalBest {
    Map<String, IntermarketQuantities>     exchangeMap =new HashMap<>();
    TreeMap<Price,IntermarketQuantities> priceMap;
    public NationalBest(){
        priceMap =new TreeMap();
    }

    public Price getBestPrice() {
        return priceMap.firstKey();
    }
    public IntermarketQuantities getBestMarket() {
        return priceMap.firstEntry().getValue();
    }
    public Collection<IntermarketQuantities> getBestMarket(Predicate<Price> filter) {
        Collection<IntermarketQuantities> result = new ArrayList<>();
        for(IntermarketQuantities mc:priceMap.values()){
            if(filter.test(mc.getPrice()))
                    result.add(mc);
        }
        return result;
    }

    public void update(String exch,Price price,int quantity){
        IntermarketQuantities mcQ = exchangeMap.get(exch);
        if (mcQ == null){
            mcQ = priceMap.get(price);

            if (mcQ == null){
                mcQ = new MarketCenterQuantities(price);
                priceMap.put(price,mcQ);
            }
            exchangeMap.put(exch,mcQ);
        }

        if (mcQ.getPrice() !=price){
            mcQ.remove(exch);

            if(mcQ.getQuantities().isEmpty())
                priceMap.remove(mcQ.getPrice());

            mcQ = priceMap.get(price);

            if (mcQ == null){
                mcQ = new MarketCenterQuantities(price);
                priceMap.put(price,mcQ);
            }
            mcQ.update(exch,price,quantity);
            exchangeMap.put(exch,mcQ);
        } else {
            mcQ.update(exch,price,quantity);
        }

    }

}

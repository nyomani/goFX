package org.next.tradable;

import org.next.api.Price;

public interface HasPrice {
    Price getPrice();
    default Side  getSide(){
        return getPrice().getSide();
    }
    default boolean hasBetterPriceThan(HasPrice other){
        return getPrice().betterThan(other.getPrice());
    }
    default boolean betterOrEqual(HasPrice other){
        return getPrice().betterOrEqual(other.getPrice());
    }
    default boolean canTradeWith(HasPrice other){
        return getPrice().canTrade(other.getPrice());
    }

}

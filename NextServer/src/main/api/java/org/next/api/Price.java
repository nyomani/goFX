package org.next.api;

import org.next.tradable.LimitPrice;
import org.next.tradable.Side;

public interface Price extends Comparable<Price> {
    static final int precision = 1000000;
    static final Price   marketSell = LimitPrice.marketSell;
    static final Price   marketBid = LimitPrice.marketBid;
    static final Price   none = LimitPrice.none;

    PriceType          getType();
    long               getValue();
    Side               getSide();
    default  int       getPrecision(){
        return  precision;
    }

    @Override
    default int compareTo(Price o) {
        return getSide().comparePrice(this,o);
    }

    default boolean isMarketPrice(){
        return getType() == PriceType.MARKET;
    }
    default boolean isNoPrice(){
        return getType() == PriceType.NONE;
    }
    default boolean betterThan(Price other){
        return getSide().betterPrice(this,other);
    }
    default boolean betterOrEqual(Price other){
        return getSide().betterOrEqual(this,other);
    }
    default boolean canTrade( Price other) {
        if(isNoPrice() || other.isNoPrice()){
            return false;
        }
        if (isMarketPrice() || other.isMarketPrice()){
            return true;
        }
        return compareTo(other) <=0;
    }

    public static Price limit(Side side,long value){
        return new LimitPrice(side,PriceType.LIMIT,value);
    }
    public static Price limit(Side side,long value,int decimalPoints){
        long newValue = value * precision/(int) Math.pow(10,decimalPoints);
        return new LimitPrice(side,PriceType.LIMIT,newValue);
    }
    public static Price limit(Side side,double number){
        long longValue = (long)(number * precision);
        return new LimitPrice(side,PriceType.LIMIT,longValue);
    }

    public static Price sellAtMarket(){
        return marketSell;
    }
    public static Price bidAtMarket(){
        return marketBid;
    }
    public static Price none(){
        return none;
    }
}


package org.next.tradable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.next.api.Price;
import org.next.api.PriceType;
@Data
@Setter(AccessLevel.PRIVATE)
public class LimitPrice implements Price {
    public static final LimitPrice marketSell = new LimitPrice(Side.CREDIT,PriceType.MARKET,0);
    public static final LimitPrice marketBid = new LimitPrice(Side.DEBIT,PriceType.MARKET,0);
    public static final LimitPrice none = new LimitPrice(Side.EVEN,PriceType.NONE,0);
    private PriceType type;
    private long      value;
    private Side      side;

    public LimitPrice (Side side,PriceType type,long value){
        this.type  = type;
        this.value = value;
        this.side  = side;
    }
    @Override
    public boolean equals(Object other){
        if (other instanceof Price){
            Price otherPrice = (Price)other;
            return type == otherPrice.getType() && value == otherPrice.getValue() && side == otherPrice.getSide();
        } else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return (int)value;
    }

    public String toString(){
        if (type == PriceType.NONE)
            return "NON PRICE";
        if (type == PriceType.MARKET)
            return "MARKET PRICE";

        return Double.valueOf((double)((double)value/precision)).toString()+" "+side;
    }
}

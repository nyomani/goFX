package org.next.tradable;

import org.next.api.Price;

import java.util.Comparator;

public class PriceComparator implements Comparator<Price> {
    @Override
    public int compare(Price p1, Price p2) {
            if (p1.isMarketPrice() && p2.isMarketPrice())
                return 0;
            if (p1.isNoPrice() && p2.isNoPrice())
                return 0;
            if (p1.isNoPrice())
                return -1;
            if (p2.isNoPrice())
                return 1;
            if (p1.isMarketPrice())
                return 1;
            if (p2.isMarketPrice())
                return -1;
            if(p1.getValue() > p2.getValue())
                return 1;
            if (p1.getValue() == p2.getValue())
                return 0;
            return -1;

        }
}

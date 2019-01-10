package org.next.tradable;

import org.next.api.Instrument;
import org.next.api.Price;

public interface TradeItem extends HasPrice {
    int   getAvailableQuantity();
    ContraFill fill(Price price,int qty);
    ItemType getKind();
    Instrument getInstrument();

}

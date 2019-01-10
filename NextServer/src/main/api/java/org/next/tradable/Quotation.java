package org.next.tradable;

import org.next.api.Instrument;
import org.next.api.Price;

public interface Quotation {
     Interest getBid();
     Interest getOffer();

    Instrument getInstrument();

    void update(Price bidPrice, Price askPrice, int bidQty, int askQty);
}

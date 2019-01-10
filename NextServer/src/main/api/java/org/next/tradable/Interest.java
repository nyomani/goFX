package org.next.tradable;

import org.next.api.Instrument;
import org.next.api.Price;

public interface Interest extends TradeItem{
	int getTradedQuantity();
	void book();
	void cancel();
	void update(Price price,int qty);
	Priority getPriority();
    Instrument getInstrument();
    long getId();
}

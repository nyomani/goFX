package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.tradable.Interest;
import org.next.tradable.Quotation;
@Builder
@Data
@Setter(AccessLevel.NONE)
public class Quote implements Quotation {
    private Interest bid;
    private Interest offer;
    private Instrument instrument;

    @Override
    public void update(Price bidPrice, Price askPrice, int bidQty, int askQty){
        bid.update(bidPrice,bidQty);
        offer.update(askPrice,askQty);
    }
}

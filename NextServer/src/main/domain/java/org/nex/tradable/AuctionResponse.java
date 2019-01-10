package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.api.UserSession;
import org.next.tradable.*;

@Data
public class AuctionResponse implements Interest {
    private ItemType kind= ItemType.AuctionResponse;
    private Price price;
    private int originalQuantity;
    private int displayQuantity;
    @Setter(AccessLevel.PRIVATE)
    private int tradedQuantity;
    @Setter(AccessLevel.PRIVATE)
    private int canceledQuantity;
    @Setter(AccessLevel.PRIVATE)
    private int availableQuantity;
    @Setter(AccessLevel.PRIVATE)
    private int minimumQuantityToTrade;
    private Priority priority;
    private BookItem bookItem;
    private UserSession session;
    private Instrument instrument;
    private long id;
    private long auctionId;

    @Override
    public void book() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void update(Price price, int qty) {

    }

    @Override
    public ContraFill fill(Price price, int qty) {
        return null;
    }
}

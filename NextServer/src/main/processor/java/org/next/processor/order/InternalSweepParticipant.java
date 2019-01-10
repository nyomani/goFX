package org.next.processor.order;

import lombok.Builder;
import org.next.api.MatchAllocator;
import org.next.api.Price;
import org.next.tradable.*;

@Builder
public class InternalSweepParticipant implements SweepParticipant{
    private PriceListBookItem  bookItem;
    private MatchAllocator     allocator;

    @Override
    public void sweep(OrderInterest order,int quantity) {
        allocator.allocate(order,bookItem);
    }

    @Override
    public Price getPrice() {
        return bookItem.getPrice();
    }
}

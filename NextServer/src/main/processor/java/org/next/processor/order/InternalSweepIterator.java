package org.next.processor.order;

import lombok.Builder;
import org.next.api.MatchAllocator;
import org.next.tradable.Auction;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.SweepParticipant;

import java.util.Iterator;
@Builder
public class InternalSweepIterator implements Iterator<SweepParticipant> {

    Iterator<PriceListBookItem> bookIterator;
    Auction          auction;
    MatchAllocator   allocator;

    @Override
    public boolean hasNext() {
        return bookIterator.hasNext();
    }

    @Override
    public SweepParticipant next() {
        if(hasNext())
            return InternalSweepParticipant.builder()
                    .allocator(allocator)
                    .bookItem(bookIterator.next())
                    .build();
        else
            return  null;
    }
}

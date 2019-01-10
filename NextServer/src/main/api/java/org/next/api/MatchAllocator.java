package org.next.api;

import org.next.tradable.Interest;
import org.next.tradable.PriceListBookItem;

public interface MatchAllocator {
    MatchReport allocate(Interest taker, PriceListBookItem rester);
}

package org.next.book;

import org.next.tradable.Auction;
import org.next.tradable.AuctionTerminateReason;
import org.next.tradable.Interest;

public interface AuctionTerminateConsumer<T extends Interest> {

    void accept(Auction a, T incoming, AuctionTerminateReason reason);
}


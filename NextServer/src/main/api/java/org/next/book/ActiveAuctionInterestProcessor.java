package org.next.book;

import org.next.tradable.Auction;
import org.next.tradable.Interest;

public interface ActiveAuctionInterestProcessor<T extends Interest> {

    void accept(Auction auction,T incoming,AuctionTerminateConsumer<T> terminateConsumer);
}

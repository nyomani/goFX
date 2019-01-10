package org.next.processor.order;

import org.next.book.ActiveAuctionInterestProcessor;
import org.next.book.AuctionTerminateConsumer;
import org.next.tradable.Auction;
import org.next.tradable.AuctionTerminateReason;
import org.next.tradable.OrderInterest;
import org.next.tradable.Side;

public class AuctionSameSideOrderProcessor implements ActiveAuctionInterestProcessor<OrderInterest> {

    @Override
    public void accept(Auction auction, OrderInterest incoming, AuctionTerminateConsumer<OrderInterest> terminator) {
        Side auctionSide = auction.getSide();
        if(auctionSide.betterPrice(incoming.getPrice(),auction.getPrice())){
            terminator.accept(auction,incoming, AuctionTerminateReason.SAME_SIDE_BETTER_PRICE);
            return;
        }

        if(incoming.getPrice().equals(auction.getPrice())){
           auction.partake(incoming);
           return;
        }
    }
}

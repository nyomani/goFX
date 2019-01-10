package org.next.processor.order;

import org.next.api.Book;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.book.ActiveAuctionInterestProcessor;
import org.next.book.AuctionTerminateConsumer;
import org.next.tradable.*;

public class AuctionOppositeSideOrderProcessor implements ActiveAuctionInterestProcessor<OrderInterest> {

    @Override
    public void accept(Auction auction, OrderInterest incoming, AuctionTerminateConsumer<OrderInterest> terminator) {
        Instrument instrument = incoming.getInstrument();

        Book book = instrument.getBook();
        Side auctionSide = auction.getSide();
        Price bestBook   = auctionSide.getBestPrice(book);
        if (bestBook.canTrade(incoming.getPrice())) {
            terminator.accept(auction, incoming, AuctionTerminateReason.INCOMING_CROSSED_BOOK);
            return;
        }

        if(auction.hasBetterPriceThan(incoming)){
            terminator.accept(auction, incoming, AuctionTerminateReason.PRICE_IMPROVED);
            return;
        }
    }

}

package org.next.processor.order;

import org.next.api.*;
import org.next.book.TradableIterator;
import org.next.tradable.*;

import java.util.Iterator;
import java.util.function.Consumer;


public class AuctionMatchProcessor implements Consumer<Auction> {

    MatchAllocator quantityAllocator;
    @Override
    public void accept(Auction auction) {
        Interest auctionedOrder = auction.getOrder();
        Instrument instrument =auctionedOrder.getInstrument();
        Book book = instrument.getBook();
        Side incomingSide = auctionedOrder.getSide();
        Side opposite = incomingSide.opposite();
        Price bestBookPrice = incomingSide.getBestPrice(book);
        if (auctionedOrder.getPrice().canTrade(bestBookPrice)) {
            match(auctionedOrder, opposite.getTradables(book, auctionedOrder.getPrice(), TradableIterator.ALL));
        }

        for (BookItem auctionResponse : auction.getAuctionResponses()) {
            auctionResponse.remove();
        }
    }

    private void match(Interest taker, Iterator<PriceListBookItem> rester){

        while (rester.hasNext()){
            PriceListBookItem list = rester.next();
            if (taker.getAvailableQuantity() >0) {
                MatchReport report = quantityAllocator.allocate(taker,list);
                report.publish();
            }
        }
    }
}

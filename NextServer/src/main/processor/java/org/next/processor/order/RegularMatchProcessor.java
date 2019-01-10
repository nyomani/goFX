package org.next.processor.order;

import org.next.api.Book;
import org.next.api.MatchAllocator;
import org.next.api.MatchReport;
import org.next.api.Price;
import org.next.book.TradableIterator;
import org.next.tradable.Interest;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.Side;

import java.util.Iterator;
import java.util.function.Consumer;

public class RegularMatchProcessor implements Consumer<Interest> {

    MatchAllocator quantityAllocator = new RegularMatchAllocator();
    public RegularMatchProcessor(){
    }

    @Override
    public void accept(Interest interest) {
        Book book = interest.getInstrument().getBook();
        Side incomingSide = interest.getSide();
        Side opposite = incomingSide.opposite();
        Price bestBookPrice=incomingSide.getBestPrice(book);
        if (interest.getPrice().canTrade(bestBookPrice)){
            match(interest,opposite.getTradables(book, interest.getPrice(),TradableIterator.ALL));
        }
    }

    private void match(Interest taker, Iterator<PriceListBookItem> rester){

        while (rester.hasNext()){
            PriceListBookItem participant = rester.next();
            if (taker.getAvailableQuantity() >0) {
                MatchReport report = quantityAllocator.allocate(taker,participant);
                report.publish();
            }
        }
    }
}

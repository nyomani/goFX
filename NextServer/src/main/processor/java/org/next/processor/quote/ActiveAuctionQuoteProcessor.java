package org.next.processor.quote;

import lombok.Builder;
import org.next.api.Instrument;
import org.next.book.ActiveAuctionInterestProcessor;
import org.next.tradable.Auction;
import org.next.tradable.AuctionTerminateReason;
import org.next.tradable.Interest;

import java.util.function.Consumer;
@Builder
public class ActiveAuctionQuoteProcessor implements Consumer<Interest> {

    ActiveAuctionInterestProcessor<Interest> sameSideProcessor;
    ActiveAuctionInterestProcessor<Interest> oppositeSideProcessor;

    @Override
    public void accept(Interest interest) {

        Instrument instrument = interest.getInstrument();
        if(!instrument.isAuctioning())
            return;

        Auction auction = instrument.getAucktion();
        if(interest.getSide() == auction.getSide())
            sameSideProcessor.accept(auction,interest,(a,e,r)->sameSideAuctionTerminator(a,e,r));
        else
            sameSideProcessor.accept(auction,interest,(a,e,r)->oppositeSideAuctionTerminator(a,e,r));

    }

    private void sameSideAuctionTerminator(Auction auction, Interest interest, AuctionTerminateReason reason){

    }
    private void oppositeSideAuctionTerminator(Auction auction, Interest interest, AuctionTerminateReason reason){

    }
}

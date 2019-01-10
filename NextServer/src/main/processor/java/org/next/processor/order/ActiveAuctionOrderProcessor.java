package org.next.processor.order;

import lombok.Builder;
import org.next.api.*;
import org.next.book.ActiveAuctionInterestProcessor;
import org.next.tradable.*;

import java.util.function.Consumer;

public class ActiveAuctionOrderProcessor implements Consumer<OrderInterest> {
    private ActiveAuctionInterestProcessor<OrderInterest> sameSideProcessor;
    private ActiveAuctionInterestProcessor<OrderInterest> oppositeSideProcessor;
    private Consumer<Auction>                             sweepProcessor;

    @Builder
    public ActiveAuctionOrderProcessor(ActiveAuctionInterestProcessor<OrderInterest> sameSideProcessor,
                                       ActiveAuctionInterestProcessor<OrderInterest> oppositeSideProcessor,
                                       Consumer<Auction>  sweepProcessor){

        this.sameSideProcessor     = sameSideProcessor;
        this.oppositeSideProcessor = oppositeSideProcessor;
        this.sweepProcessor        = sweepProcessor;
    }

    @Override
    public void accept(OrderInterest incoming) {
        Instrument instrument = incoming.getInstrument();
        if (instrument.isAuctioning()) {
            Auction auction = instrument.getAucktion();
            if (auction.getSide() == incoming.getSide())
                sameSideProcessor.accept(auction, incoming, (a, o, r) -> sameSidePrematureEnding(a, o, r));
            else
                oppositeSideProcessor.accept(auction, incoming, (a, o, r) -> oppositeSidePrematureEnding(a, o, r));
        }
    }

    /*
      Same side order will not participate in the auction trade.
     */
    private void sameSidePrematureEnding(Auction a, OrderInterest incoming, AuctionTerminateReason reason){
        a.terminate(reason);
        sweepProcessor.accept(a);
        a.getOrder().book();
    }
    /*
      Auction prematurely ended by opposite side order. Book the incoming order and sweep will includes incoming
      order in the trade
     */
    private void oppositeSidePrematureEnding(Auction a,OrderInterest incoming,AuctionTerminateReason reason){
        incoming.book();
        a.terminate(reason);
        sweepProcessor.accept(a);
        a.getOrder().book();
    }
}

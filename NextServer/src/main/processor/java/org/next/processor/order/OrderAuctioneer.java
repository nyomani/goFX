package org.next.processor.order;

import lombok.Builder;
import org.next.tradable.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Builder
public class OrderAuctioneer implements Consumer<OrderInterest> {

    private Consumer<Auction>        auctionMatchProcessor;
    private Consumer<Interest>       regNMSProcessor;
    private Consumer<AuctionTask>    auctionTimer;
    private Predicate<OrderInterest> auctionValidator;

   @Builder.Default final Map<Long,Auction> auctionMap = new HashMap<>();


    @Override
    public void accept(OrderInterest order) {

        if (auctionValidator.test(order))
            startAuction(order);
    }


    private void startAuction(OrderInterest order){
        int duration = 10;
        Auction auction = order.createAuction();
        auction.start(duration);
        auctionTimer.accept(new AuctionTask(a-> expireAuction(a),auction,duration));
    }

    private void expireAuction(Auction a){

        a.terminate(AuctionTerminateReason.EXPIRED);
        auctionMatchProcessor.accept(a);
        regNMSProcessor.accept(a.getOrder());
    }

}

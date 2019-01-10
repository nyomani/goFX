package org.next.processor.order;

import org.next.tradable.AuctionTask;

import java.util.Timer;
import java.util.function.Consumer;

public class AuctionTimer implements Consumer<AuctionTask> {

    private Timer   auctionTimer = new Timer("Auction Timer",true);

    @Override
    public void accept(AuctionTask auctionTask) {
        auctionTimer.schedule(auctionTask,auctionTask.getDuration());
    }

    public String toString(){
        return auctionTimer.toString();
    }
}

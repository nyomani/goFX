package org.next.tradable;

import org.next.tradable.Auction;

import java.util.TimerTask;
import java.util.function.Consumer;

public class AuctionTask extends TimerTask {
    private Consumer<Auction> consumer;
    private Auction auction;
    private int duration;
    public AuctionTask(Consumer<Auction> consumer, Auction a,int duration){
        this.consumer =consumer;
        this.auction =a;
    }

    @Override
    public void run() {
        consumer.accept(auction);
    }

    public int getDuration(){
        return duration;
    }
}

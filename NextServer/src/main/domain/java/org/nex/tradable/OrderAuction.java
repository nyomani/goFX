package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.tradable.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderAuction implements Auction {
    @Setter(AccessLevel.PRIVATE)
    long auctionId;
    @Setter(AccessLevel.PRIVATE)
    LocalDateTime startTime;
    @Setter(AccessLevel.PRIVATE)
    LocalDateTime terminateTime;
    @Setter(AccessLevel.PRIVATE)
    LocalDateTime endTime;
    @Setter(AccessLevel.PRIVATE)
    OrderInterest order;
    @Setter(AccessLevel.PRIVATE)
    AuctionTerminateReason terminateReason;
    @Setter(AccessLevel.PRIVATE)
    AuctionState state;

    @Setter(AccessLevel.PRIVATE)
    Price  price;
    @Setter(AccessLevel.PRIVATE)
    int    auctionQuantity;

    public OrderAuction(OrderInterest order){
        this.order = order;
    }

    @Override
    public List<BookItem> getAuctionResponses() {
        return null;
    }

    @Override
    public void terminate(AuctionTerminateReason reason) {
        this.terminateReason = reason;
        this.terminateTime = LocalDateTime.now();
        this.state = AuctionState.TERMINATED;
        order.getInstrument().auctionTerminated();
    }

    @Override
    public void start(int duration) {
        this.auctionQuantity=order.getAvailableQuantity();
        this.price=order.getPrice();
        this.startTime = LocalDateTime.now();
        this.endTime =startTime.plusSeconds(duration);
        this.state = AuctionState.STARTED;
        order.getInstrument().auctionStarted(this);
    }

    @Override
    public void partake(OrderInterest order) {

    }
    @Override
    public int getAvailableQuantity() {
        return order.getAvailableQuantity();
    }

    @Override
    public ContraFill fill(Price price, int qty) {
        return order.fill(price,qty);
    }

    @Override
    public ItemType getKind() {
        return order.getKind();
    }

    @Override
    public Instrument getInstrument() {
        return order.getInstrument();
    }
}

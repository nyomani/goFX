package org.next.processor.order;

import lombok.Builder;
import org.next.tradable.OrderInterest;
import org.next.tradable.Side;
import org.next.tradable.SweepParticipant;

import java.util.Iterator;

public class SweepIterator<T extends SweepParticipant> {
    private Iterator<T>  intermarketParticipant;
    private Iterator<T> internalParticipant;
    private Side side;
    private T current;
    private T intermarket;
    private T internal;

    @Builder
    private SweepIterator(Side side,Iterator<T>  intermarketParticipant,Iterator<T>  internalParticipant){
        this.side =side;
        this.intermarketParticipant = intermarketParticipant;
        this.internalParticipant = internalParticipant;
        this.internal = internalParticipant.next();
        this.intermarket=intermarketParticipant.next();
        nextItem();
    }

    void sweep(OrderInterest order){
        while(order.getAvailableQuantity() > 0 && current != null){
            current.sweep(order,order.getAvailableQuantity());
            nextItem();
        }

    }

   private void nextItem(){
        if (intermarket == null && internal==null)
            current = null;

        if (intermarket == null) {
            current = internal;
            internal = internalParticipant.next();
        }
        else
        if (internal == null) {
            current = intermarket;
            intermarket=intermarketParticipant.next();
        }
        else if (internal.betterOrEqual(intermarket)) {
            current = internal;
            internal = internalParticipant.next();
        } else {
            current = intermarket;
            intermarket = intermarketParticipant.next();
        }

   }

}

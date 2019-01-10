package org.next.tradable;

public interface SweepParticipant extends HasPrice {
    void sweep(OrderInterest order,int quantity);
}

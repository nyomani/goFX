package org.next.processor.order;

import org.next.api.IntermarketGateway;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;
import org.next.tradable.OrderInterest;
import org.next.tradable.SweepParticipant;

public class IntermarketSweepParticipant implements SweepParticipant {

    private IntermarketQuantities  intermarketQuote;
    private IntermarketGateway     gateway;

    IntermarketSweepParticipant(IntermarketQuantities  intermarketQuote,IntermarketGateway     gateway){
        this.intermarketQuote = intermarketQuote;
        this.gateway = gateway;
    }

    @Override
    public void sweep(OrderInterest order,int quantity) {
        gateway.accept(intermarketQuote,order,quantity);
    }

    @Override
    public Price getPrice() {
        return intermarketQuote.getPrice();
    }

}

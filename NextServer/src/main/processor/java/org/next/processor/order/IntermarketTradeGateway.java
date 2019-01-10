package org.next.processor.order;

import lombok.Builder;
import org.next.api.IntermarketGateway;
import org.next.api.IntermarketQuantities;
import org.next.api.Session;
import org.next.tradable.OrderInterest;

@Builder
public class IntermarketTradeGateway implements IntermarketGateway {
    Session session;

    @Override
    public void accept(IntermarketQuantities mc, OrderInterest order, int quantity) {
        order.route(mc,e->e.route(session));
    }

}

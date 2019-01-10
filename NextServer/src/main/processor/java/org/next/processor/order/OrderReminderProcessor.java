package org.next.processor.order;

import org.next.tradable.OrderInterest;
import org.next.tradable.TradableState;

import java.util.function.Consumer;

public class OrderReminderProcessor implements Consumer<OrderInterest> {
    @Override
    public void accept(OrderInterest orderInterest) {
         if(orderInterest.getState() == TradableState.NONE && orderInterest.getAvailableQuantity() > 0)
             orderInterest.cancel();
    }
}

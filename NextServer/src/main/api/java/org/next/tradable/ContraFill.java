package org.next.tradable;

import org.next.api.Price;

public interface ContraFill {
    Interest getInterest();
    int      getQuantity();
    Price getPrice();
}

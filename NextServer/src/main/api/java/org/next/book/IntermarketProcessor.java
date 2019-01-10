package org.next.book;

import org.next.tradable.OrderInterest;

import java.util.function.Consumer;

public interface IntermarketProcessor extends Consumer<OrderInterest> {
}

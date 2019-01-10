package org.next.book;

import org.next.tradable.Interest;

import java.util.function.Consumer;

public interface TradableProcessor extends Consumer<Interest> {
	void accept(Interest t);

}

package org.next.book;

import org.next.tradable.Interest;

import java.util.function.Consumer;

public interface BookProcessor  extends Consumer<Interest> {
}

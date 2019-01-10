package org.next.api;

import java.util.Iterator;

public interface HasPriceIterator<T> extends Iterator<T> {
    Price getPrice();
}

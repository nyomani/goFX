package org.next.tradable

import org.next.api.Price
import org.next.util.LinkList

public interface BookItem  {
       Interest getInterest();
       void remove();

       BookItem update(Price price, int quantity);
       long getItemKey();

       int getBookedQuantity();
       Price getBookedPrice();

       def void linkTo(LinkList<BookItem> list)
}

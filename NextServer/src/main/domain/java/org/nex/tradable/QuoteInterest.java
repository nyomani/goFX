package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.tradable.*;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class QuoteInterest implements Interest {
    private int tradedQuantity;
    private int availableQuantity;
    private Price price;
    private Instrument instrument;
    private Priority priority;
    private long id;
    private ItemType kind = ItemType.Quote;
    private BookItem bookItem;

    @Override
    public void book(){

        if(bookItem == null){
            bookItem = instrument.getBook().book(this,price,availableQuantity);
        } else {
            bookItem = bookItem.update(price,availableQuantity);
        }

    }

    @Override
    public ContraFill fill(Price price,int quantity){
        return null;
    }

    @Override
    public void cancel(){
        bookItem.remove();
        bookItem = null;
    }

    @Override
    public void update(Price price,int quantity){
        if (quantity == 0)
            cancel();
        else{
            bookItem = bookItem.update(price,quantity);
        }
    }
}

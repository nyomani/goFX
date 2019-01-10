package org.next.tradable;

import lombok.Data;
import org.next.api.Instrument;
import org.next.api.Price;
import org.next.api.Session;

@Data
public class IntermarketOrder implements IntermarketInterest {
    int availableQuantity;
    ItemType kind;
    Instrument instrument;
    Price price;



    public ContraFill fill(Price price,int quantity){
        return null;
    }

    @Override
    public void route(Session s) {

    }
}

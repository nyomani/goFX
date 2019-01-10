package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.next.api.Price;
import org.next.tradable.ContraFill;
import org.next.tradable.Interest;
@Data
@Setter(AccessLevel.PRIVATE)
public class ContraFillReport implements ContraFill {
    private Interest interest;
    private Price price;
    private int quantity;

    public ContraFillReport(Interest interest, Price price, int quantity){
        this.interest = interest;
        this.price = price;
        this.quantity = quantity;
    }

    void commit(){
        interest.fill(price,quantity);
    }
}


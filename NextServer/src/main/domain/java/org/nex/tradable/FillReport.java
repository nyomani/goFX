package org.nex.tradable;

import org.next.api.MatchReport;
import org.next.api.Price;
import org.next.tradable.BookItem;
import org.next.tradable.Interest;
import org.next.tradable.TradeType;

import java.util.ArrayList;
import java.util.List;

public class FillReport implements MatchReport {

    private Interest taker;
    private List<ContraFillReport> makers =new ArrayList<>();
    private int       filledQuantity;
    private Price     price;
    private TradeType tradeType;
    private int       neededQuantity;

    public FillReport(TradeType type, Interest taker, Price price){
        this.tradeType = type;
        this.price = price;
        this.taker = taker;
        this.neededQuantity = taker.getAvailableQuantity();
    }

    public void addContra(BookItem t){
        int quantity = t.getBookedQuantity();
        if(quantity > neededQuantity)
            quantity = neededQuantity;
        makers.add(new ContraFillReport(t.getInterest(),price,quantity));
        this.filledQuantity +=quantity;
        this.neededQuantity -=quantity;
    }

    public boolean needMoreQuantity(){
        return neededQuantity > 0;
    }

    @Override
    public void publish(){
        commit();
        publishFillReport();
        publishLastSale();
        publishTradeReport();
    }

    private void commit(){
        taker.fill(price,filledQuantity);
        for(ContraFillReport contra:makers){
            contra.commit();
        }
    }
    private void publishLastSale(){

    }

    private void publishTradeReport(){

    }

    private void publishFillReport(){

    }
}
package org.nex.tradable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.next.api.*;
import org.next.tradable.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Consumer;

@Data()
@Builder
@Setter(AccessLevel.NONE)
public class Order implements OrderInterest {
	private ItemType kind= ItemType.Order;
	private Price price;
    private int   originalQuantity;
    private int   displayQuantity;
    private int   tradedQuantity;
    private int   canceledQuantity;
    private int   availableQuantity;
    private int   minimumQuantityToTrade;
    private Priority    priority;
    private BookItem    bookItem;
    private UserSession session;
    private Instrument  instrument;
    private long        id;
    private TradableState state;

   public  Order (UserSession session,Instrument instrument){
    	this.session = session;
    	this.instrument = instrument;
    }

    public Order setOriginalQuantity(int qty){
   		originalQuantity = qty;
   		if(displayQuantity ==0){
   			displayQuantity = qty;
		}
   		availableQuantity=originalQuantity;
   		return this;
	}
   
	@Override
	public void cancel() {
		LocalDateTime time = LocalDateTime.now();
   	    canceledQuantity = availableQuantity;
   	    availableQuantity =0;
   	    displayQuantity=0;
		if(bookItem !=null) {
			bookItem.remove();
		}
	}
	
	@Override
	public void update(Price price, int qty) {
		LocalDateTime time = LocalDateTime.now();
		canceledQuantity=(originalQuantity > qty?originalQuantity-qty:0);
		this.availableQuantity=qty;
		this.displayQuantity=Math.min(qty,displayQuantity);
		this.price = price;
		if(bookItem != null)
			updateOrRemove();
	}

	@Override
	public ContraFill fill( Price price, int qty) {
   	    if (qty > availableQuantity)
   	    	qty = availableQuantity;

		availableQuantity -=qty;
        tradedQuantity +=qty;
        displayQuantity = Math.min(displayQuantity,availableQuantity);

        if (bookItem !=null)
        	updateOrRemove();

        return new ContraFillReport(this,price,qty);
	}

	@Override
	public void book() {
		if (bookItem !=null) {
			updateOrRemove();
		}else {
			if (availableQuantity > 0)
				bookItem = instrument.getBook().book(this, price, displayQuantity);
		}
	}

	private void updateOrRemove(){
		if (availableQuantity > 0)
			bookItem = bookItem.update(price, displayQuantity);
		else {
			bookItem.remove();
			bookItem = null;
		}

	}
	@Override
	public Auction createAuction() {
		OrderAuction auction = new OrderAuction(this);
		return auction;
	}

	@Override
	public void route(IntermarketQuantities mc, Consumer<IntermarketInterest> consumer) {
       Collection<MarketCenter> marketCenterQuantities = mc.getQuantities();
       for (MarketCenter marketCenter:marketCenterQuantities){
       	if(getAvailableQuantity() > 0) {
			int mcQuantity = marketCenter.getQuantity();
			int sendQty = Math.min(getAvailableQuantity(), mcQuantity);
			availableQuantity -= sendQty;
			IntermarketOrder routedOrder = new IntermarketOrder();
			consumer.accept(routedOrder);
		} else
			break;
	   }
	}

	@Override
	public void routeCancel(int quantity) {
   		availableQuantity +=quantity;
	}
}

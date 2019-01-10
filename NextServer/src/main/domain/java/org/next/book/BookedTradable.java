package org.next.book;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.next.api.Price;
import org.next.api.PriceChangeObserver;
import org.next.util.LinkList;
import org.next.util.ListNode;
import org.next.tradable.BookItem;
import org.next.tradable.Interest;
@Data
@Setter(AccessLevel.NONE)
public class BookedTradable implements BookItem {
	private Interest interest;
	private int bookedQuantity;
	private Price bookedPrice;
	private PriceChangeObserver priceChangeObserver;
	private long itemKey;
	private ListNode<BookedTradable> node;

	BookedTradable(Interest t, Price price, int bookQuantity ,PriceChangeObserver priceChangeObserver) {
		this.interest = t;
		this.bookedQuantity = bookQuantity;
		this.bookedPrice = price;
		this.itemKey = t.getId();
		this.priceChangeObserver = priceChangeObserver;

	}
	@Override
	public void remove() {
        if(node !=null)
			node.removeFromList();
	}

	@Override
	public BookItem update(Price price, int quantity) {
		BookItem item = this;
		if(price != bookedPrice){
			Price oldPrice = this.bookedPrice;
			int   oldQuantity=this.bookedQuantity;
			this.bookedPrice = price;
			this.bookedQuantity = quantity;
			node.removeFromList();
			item = priceChangeObserver.priceUpdated(this,oldPrice,oldQuantity);
		}
		return item;
	}

	public void addPriceChangeObserver(PriceChangeObserver observer) {
		this.priceChangeObserver = observer;
	}


	public void linkTo(LinkList<BookItem> list) {
		node = new ListNode<>(this);
		node.linkTo(list);
	}
}

package org.next.book;

import org.next.api.ListObeserver;
import org.next.api.Price;
import org.next.tradable.BookItem;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.SideBookItem;
import org.next.tradable.Interest;
import org.next.util.LinkList;
import org.next.util.Node;

import java.util.function.Predicate;

public class BookItemList implements ListObeserver {
	private LinkList<BookItem>  list = new LinkList(this);
	private SideBookItem   parent;
	private Price price;

	public BookItemList(Price p, SideBookItem parent){
		this.price = p;
		this.parent = parent;
	}

	public BookItem add(Interest item, int quantity) {
		BookedTradable bookItem = new BookedTradable(item,price, quantity,parent);

		bookItem.linkTo(list);
		return bookItem;
	}
	public void add(BookItem item){
		item.linkTo(list);
	}

	public Price getPrice() {
		return price;
	}

	@Override
	public void listIsEmpty() {
		parent.remove(price);
	}


	public PriceListBookItem iterator(Predicate<BookItem> filter) {
		Node<BookItem> t= list.getRoot();
		return new TradableIterator(t,filter);
	}

}

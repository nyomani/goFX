package org.next.book;

import org.next.api.Book;
import org.next.api.Price;
import org.next.tradable.BookItem;
import org.next.tradable.Interest;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.Side;

import java.util.Collection;
import java.util.function.Predicate;

public class TradableBook implements Book {
    private BookSide buySide  = new BookSide(Side.DEBIT);
    private BookSide sellSide = new BookSide(Side.CREDIT);
    
	@Override
	public BookItem book(Interest item, Price price, int quantity) {
		if (item.getSide() == Side.DEBIT) {
			return buySide.add(item,price,quantity);
		} else {
			return sellSide.add(item,price,quantity);
		}
	}

	@Override
	public Price getBestBidPrice() {
		return buySide.getBestPrice();
	}

	@Override
	public Price getBestAskPrice() {
		return sellSide.getBestPrice();
	}

	@Override
	public Collection<PriceListBookItem> getBids(Predicate<Price> priceFilter, Predicate<BookItem> filter) {
		return buySide.getTradables(priceFilter,filter);
	}

	@Override
	public  Collection<PriceListBookItem>  getAsks(Predicate<Price> priceFilter, Predicate<BookItem> filter) {
		return sellSide.getTradables(priceFilter,filter);
	}

	@Override
	public PriceListBookItem getBidsAtPrice(Price priceFilter, Predicate<BookItem> filter) {
		return buySide.getTradablesAtPrice(priceFilter,filter);
	}

	@Override
	public  PriceListBookItem getAsksAtPrice(Price priceFilter, Predicate<BookItem> filter) {
		return sellSide.getTradablesAtPrice(priceFilter,filter);
	}
}

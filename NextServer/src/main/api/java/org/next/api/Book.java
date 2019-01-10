package org.next.api;

import org.next.book.TradableIterator;
import org.next.tradable.BookItem;
import org.next.tradable.Interest;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.SweepParticipant;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface Book {

	public BookItem book(Interest item, Price price, int quantity);
	public Price getBestBidPrice();
	public Price getBestAskPrice();
	public Collection<PriceListBookItem> getBids(Predicate<Price> priceFilter, Predicate<BookItem> filter);
	public Collection<PriceListBookItem> getAsks(Predicate<Price> priceFilter, Predicate<BookItem> filter);

	public PriceListBookItem getBidsAtPrice(Price priceFilter, Predicate<BookItem> filter);
	public PriceListBookItem getAsksAtPrice(Price priceFilter, Predicate<BookItem> filter);

} 

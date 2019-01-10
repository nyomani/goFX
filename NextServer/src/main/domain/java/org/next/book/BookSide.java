package org.next.book;

import org.next.api.Price;
import org.next.tradable.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

public class BookSide implements SideBookItem{
	private Side side;
	TreeMap<Price, BookItemList> priceList	;

   public BookSide(Side side){
		this.side = side;
		priceList = new TreeMap();
   }

   @Override
   public BookItem add(Interest item, Price price, int quantity) {
	   BookItemList list = priceList.get(price);
	   if (list == null) {
		   list = new BookItemList(price,this);
		   priceList.put(price, list);
	   }
	   BookItem bookItem = list.add(item,quantity);
	   return bookItem;
   }

	@Override
	public void remove(Price key) {

		BookItemList list = priceList.remove(key);
		if(list == null){
			System.out.println("Price "+key +" is not found");
		}
	}

	@Override
	public BookItem priceUpdated(BookItem t, Price oldPrice,int oldQuantity) {
		BookItemList list = priceList.get(t.getBookedPrice());
		if (list == null) {
			list = new BookItemList(t.getBookedPrice(),this);
			priceList.put(t.getBookedPrice(), list);
		}
		list.add(t);
		return t;
	}

	public Price getBestPrice() {
   	   if(priceList.isEmpty()){
   	   	return side.getWorsePrice();
	   }
   	   return priceList.firstKey();
	}
	public PriceListBookItem getTradablesAtPrice(Price priceFilter, Predicate<BookItem> filter) {
		BookItemList bookItemList = priceList.get(priceFilter);
		if(bookItemList !=null){
			return bookItemList.iterator(filter);
		} else {
			return TradableIterator.EMPTY_ITERATOR;
		}
	}

	public Collection<PriceListBookItem>  getTradables(Predicate<Price> priceFilter, Predicate<BookItem> filter) {
		ArrayList<PriceListBookItem> tradableList = new ArrayList<>();
		for (BookItemList list:priceList.values()){
			if (priceFilter.test(list.getPrice())){
				PriceListBookItem iter = list.iterator(filter);
				if (iter.hasNext())
					tradableList.add(iter);
			}
		}

		return tradableList;
	}

	public Set<Price> getPrices(){
   		return priceList.navigableKeySet();
	}


}


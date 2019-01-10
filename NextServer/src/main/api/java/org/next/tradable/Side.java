package org.next.tradable;

import org.next.api.Book;
import org.next.api.Intermarket;
import org.next.api.IntermarketQuantities;
import org.next.api.Price;

import java.util.Iterator;
import java.util.function.Predicate;

public enum Side implements Sider{
       DEBIT(new Buyer()), CREDIT(new Seller()),EVEN(new Buyer());

       Sider side;

       Side(Sider s){
              this.side = s;
       }

       @Override
       public Price getBestPrice(Book book) {
              return side.getBestPrice(book);
       }
       @Override
       public Price getBestPrice(Intermarket market) {
              return side.getBestPrice(market);
       }

       @Override
       public Iterator<PriceListBookItem> getTradables(Book book, Price priceConstraints, Predicate<BookItem> filter) {
              return side.getTradables(book,priceConstraints,filter);
       }

       @Override
       public Iterator<IntermarketQuantities> getNationMarkets(Intermarket market, Predicate<Price> filter) {
              return side.getNationMarkets(market,filter);
       }

       @Override
       public int comparePrice(Price p1, Price p2) {
              return side.comparePrice(p1,p2);
       }
       @Override
       public boolean betterPrice(Price p1, Price p2) {
              return side.comparePrice(p1,p2) <0;
       }

       @Override
       public Side opposite() {
              return side.opposite();
       }

       public Price limit(long value){
              return Price.limit(this,value);
       }
       public Price limit(long value,int decimalPoints){
              return Price.limit(this,value,decimalPoints);
       }
       public Price limit(double number){
              return Price.limit(this,number);

       }


}


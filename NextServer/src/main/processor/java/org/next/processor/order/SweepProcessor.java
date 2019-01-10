package org.next.processor.order;

import lombok.Builder;
import org.next.api.*;
import org.next.tradable.Auction;
import org.next.tradable.OrderInterest;
import org.next.tradable.Side;

import java.util.function.Consumer;
@Builder
public class SweepProcessor implements Consumer<Auction> {

    private IntermarketGateway intermarketGateway;
    private MatchAllocator     allocator;
    @Override
    public void accept(Auction a) {

            OrderInterest order =a.getOrder();
            Instrument instrument = a.getInstrument();
            Book book = instrument.getBook();
            Intermarket nbbo = instrument.getNBO();
            final Side side = a.getSide().opposite();
            final Price bestBook = side.getBestPrice(book);

            InternalSweepIterator internalSweepIterator = InternalSweepIterator.builder()
                    .bookIterator(side.getTradables(book,order.getPrice(),e->true))
                    .auction(a)
                    .allocator(allocator)
                    .build();

            IntermarketSweepIterator intermarketSweepIterator = IntermarketSweepIterator.builder()
                    .intermarketIterator(side.getNationMarkets(nbbo,p->p.betterThan(bestBook)))
                    .intermarketGateway(intermarketGateway).build();

            SweepIterator.builder()
                    .intermarketParticipant(intermarketSweepIterator)
                    .internalParticipant(internalSweepIterator)
                    .build()
                    .sweep(order);


        }
}

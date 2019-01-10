package org.next.processor.order;

import lombok.Builder;
import org.next.api.Instrument;
import org.next.tradable.Interest;
import org.next.tradable.OrderInterest;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Builder
public class OrderProcessor implements Consumer<OrderInterest> {
	private Predicate<OrderInterest> validationProcessor;
	private Consumer<OrderInterest>  activeAuctionOrderProcessor;
	private Consumer<Interest>       sweepBookProcessor;
    private Consumer<Interest>       auctionProcessor;
	private Consumer<Interest>       reminderProcessor;

	public void accept(OrderInterest t) {

		if(!validationProcessor.test(t))
			return;

		Instrument instrument = t.getInstrument();
		instrument.lock();
		{
			activeAuctionOrderProcessor.accept(t);
			sweepBookProcessor.accept(t);
			auctionProcessor.accept(t);
			t.book();
		}
		instrument.release();
		reminderProcessor.accept(t);
	}
}

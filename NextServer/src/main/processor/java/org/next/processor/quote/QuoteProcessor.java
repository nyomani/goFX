package org.next.processor.quote;

import lombok.Builder;
import org.next.api.Instrument;
import org.next.book.RegularMatchProcessor;
import org.next.tradable.Interest;
import org.next.tradable.Quotation;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Builder
public class QuoteProcessor implements Consumer<Quotation> {
    private RegularMatchProcessor matchingProcessor;
    private Predicate<Quotation>  quoteValidator;
    private Consumer<Interest>    activeActionProcessor;

	@Override
	public void accept(Quotation quote) {
		if(!quoteValidator.test(quote)){
			return;cd
		}
		Instrument instrument =quote.getInstrument();
		instrument.lock();
		{
			accept(quote.getBid());
			accept(quote.getOffer());
		}
		instrument.lock();
	}

	private void accept(Interest t){
		activeActionProcessor.accept(t);
		matchingProcessor.accept(t);
		t.book();
	}
}

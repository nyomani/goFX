package org.next.processor.order;

import lombok.Builder;
import org.next.api.IntermarketGateway;
import org.next.api.IntermarketQuantities;
import org.next.tradable.SweepParticipant;

import java.util.Iterator;
@Builder
public class IntermarketSweepIterator implements Iterator<SweepParticipant> {
    private IntermarketGateway intermarketGateway;
    Iterator<IntermarketQuantities> intermarketIterator;

    @Override
    public boolean hasNext() {
        return intermarketIterator.hasNext();
    }

    @Override
    public IntermarketSweepParticipant next() {
        if(hasNext())
            return new IntermarketSweepParticipant(intermarketIterator.next(),intermarketGateway);
        else
            return null;
    }
}


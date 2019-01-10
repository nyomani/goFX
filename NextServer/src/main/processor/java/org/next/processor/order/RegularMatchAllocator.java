package org.next.processor.order;

import org.nex.tradable.FillReport;
import org.next.api.MatchAllocator;
import org.next.api.MatchReport;
import org.next.tradable.Interest;
import org.next.tradable.PriceListBookItem;
import org.next.tradable.TradeType;

public class RegularMatchAllocator implements MatchAllocator {

    @Override
    public MatchReport allocate(Interest taker, PriceListBookItem rester) {
        FillReport fillReport = new FillReport(TradeType.REGULAR, taker, rester.getPrice());
        while (rester.hasNext() && fillReport.needMoreQuantity()) {
            fillReport.addContra(rester.next());
        }

        return fillReport;
    }
}

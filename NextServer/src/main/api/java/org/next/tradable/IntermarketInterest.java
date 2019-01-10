package org.next.tradable;

import org.next.api.Session;
import org.next.api.UserSession;

public interface IntermarketInterest extends TradeItem {

    void route(Session s);
}

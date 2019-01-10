package org.nex.tradable;

import lombok.Data;
import org.next.tradable.Side;
import org.next.tradable.ItemType;

@Data
public class CancelReport {
     long tradableId;
     ItemType type;
     Side side;
     int cancelledQuantity;
     int remainingQuantity;
}

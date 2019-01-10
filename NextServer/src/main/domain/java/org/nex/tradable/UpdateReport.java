package org.nex.tradable;

import lombok.Data;

@Data
public class UpdateReport {
	private long instrumentId;
	private String userAssignedId;
    private long tradableId;
    private int  fillType;
    private char side;
    private int  originalQuantity;
    private int  totalFilledQuantity;
    private int  lastFillPrice;
    private int  leavesQuantity;
}

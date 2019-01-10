package org.next.api;

public interface AssetClass {
    long       getId();
    AssetType  getType();
    String     getSymbol();
    SettlementType getSettlementType();
}

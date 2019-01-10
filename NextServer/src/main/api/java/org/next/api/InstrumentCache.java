package org.next.api;

public interface InstrumentCache {
    void       create(byte[] data);
    Instrument find(long id);

}

package org.next.api;

public interface GlobalIdentityProvider {
    Range<Long> get(String context);
}

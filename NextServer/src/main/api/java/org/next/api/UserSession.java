package org.next.api;

public interface UserSession {
	User getUser();
	public void send(byte[] data);
}

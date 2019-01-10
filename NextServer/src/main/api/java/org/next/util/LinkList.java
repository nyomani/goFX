package org.next.util;

import org.next.api.ListObeserver;
import org.w3c.dom.NodeList;

public class LinkList <T>{
	ListNode<T> root;
	ListObeserver observer;
	public LinkList(ListObeserver o){
		this.observer =o;
	}

	void link(ListNode<T> value) {
		if (root ==null) {
			root = value;
		} else {
			root.link(value);
		}
	}
	
	void unlink(ListNode<T> n) {
		ListNode<T> next = n.unlink();
		if (root == n) {
			root = next;
		}
		if(isEmpty())
			observer.listIsEmpty();
	}

	boolean isEmpty() {
		return root == null;
	}
	public  Node<T> getRoot(){
		return root;
	}
}

package org.next.util;

public class ListNode<T> implements Node<T>{
	ListNode<T> next;
	ListNode<T> prev;
	LinkList<T> parent;
    T value;

	public ListNode(T value){
		this.value = value;
	}
	ListNode<T> unlink(){
		if (prev !=null)
		    prev.next = next;
		if (next !=null)
		    next.prev=prev;
		return next;
	}
	void link( ListNode<T> value) {
		this.next= value;
		value.prev=this;
	}

	public void linkTo(LinkList parent){
		this.parent = parent;
		parent.link(this);

	}
	public void removeFromList(){
		parent.unlink(this);
	}

	public ListNode<T> getNext(){
		return next;
	}

	@Override
	public T value() {
		return value;
	}
}

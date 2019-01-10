package org.next.util;

public interface Node<T> {
    public Node<T> getNext();
    public T value();
}

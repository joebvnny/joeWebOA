package com.joebase.datastructs;

public interface Queue<E> {

    public void clear();
    public boolean isEmpty();
    public int size();
    public E peek();
    public boolean offer(E e);
    public E poll();
    public void display();
}

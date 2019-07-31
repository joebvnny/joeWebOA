package com.joebase.datastructs;

public interface Stack<E> {

    public void clear();
    public boolean isEmpty();
    public int size();
    public E peek();
    public boolean push(E e);
    public E pop();
    public void display();
}

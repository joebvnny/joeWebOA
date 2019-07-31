package com.joebase.datastructs;

public interface List<E> {
    
    public void clear();
    public boolean isEmpty();
    public int size();
    public E get(int i);
    public boolean insert(int i, E e);
    public boolean remove(int i);
    public boolean update(int i, E x);
    public void display();
}

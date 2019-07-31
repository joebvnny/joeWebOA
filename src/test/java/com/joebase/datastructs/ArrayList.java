package com.joebase.datastructs;

public class ArrayList<T> implements List<T> {
    
    private Object[] elem;
    private final static int INIT_SIZE = 10;
    private final static int INCREMENT = 5;
    private int size;
    
    public ArrayList() {
        elem = new Object[INIT_SIZE];
        size = 0;
    }
    
    public ArrayList(int initSize) {
        elem = new Object[initSize];
        size = 0;
    }

    @Override
    public void clear() {
        elem = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int i) {
        if(i<0 || i>size-1) return null;
        return (T)elem[i];
    }

    @Override
    public boolean insert(int i, T e) {
        if(i<0 || i>size) return false;
        if(size+1 > elem.length) ensureCapacity();
        for(int j=size; j>i; j--) {
            elem[j] = elem[j-1];
        }
        elem[i] = e;
        size++;
        return true;
    }

    private void ensureCapacity() {
        System.out.println("ensureCapacity()");
        int newSize = elem.length + INCREMENT;
        Object[] newArray = new Object[newSize];
        System.arraycopy(elem, 0, newArray, 0, elem.length);
        elem = newArray;
    }

    @Override
    public boolean remove(int i) {
        if(i<0 || i>size-1) return false;
        for(int j=i; j<size-1; j++) {
            elem[j] = elem[j+1];
        }
        size--;
        return true;
    }

    @Override
    public boolean update(int i, T x) {
        if(i<0 || i>size-1) return false;
        elem[i] = x;
        return true;
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("[]");
        else {
            sb.append("[");
            for(int i=0; i<size; i++) {
                sb.append(elem[i] + ", ");
            }
            System.out.println(sb.substring(0, sb.lastIndexOf(", ")) + "]");
        }
    }

}
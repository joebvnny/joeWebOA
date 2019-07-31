package com.joebase.datastructs;

public class CircleQueue<T> implements Queue<T> {
    
    private Object[] elem;
    private final static int INIT_SIZE = 5;
    private int head;
    private int tail;
    private int size;

    public int getHead() {
        return head;
    }
    
    public int getTail() {
        return tail;
    }

    public CircleQueue() {
        elem = new Object[INIT_SIZE];
        head = tail = 0;
        size = 0;
    }
    
    @Override
    public void clear() {
        head = tail = 0;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
        //return tail==head;
    }
    
    public boolean isFull() {
        return size==elem.length;
        //return (tail+1)%elem.length==head;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if(isEmpty()) return null;
        return (T)elem[head];
    }

    @Override
    public boolean offer(T e) {
        if(isFull()) return false;
        else {
            elem[tail] = e;
            tail = (tail+1) % elem.length;
            ++size;
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T poll() {
        if(isEmpty()) return null;
        else {
            T t = (T)elem[head];
            head = (head+1) % elem.length;
            --size;
            return t;
        }
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("<<");
        else {
            sb.append("<");
            for(int i=head; i!=tail; i=(i+1)%elem.length) {
                sb.append(elem[i] + ", ");
            }
            sb.append("<");
            System.out.println(sb.toString());
        }
    }

}
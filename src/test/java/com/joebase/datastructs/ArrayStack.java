package com.joebase.datastructs;

public class ArrayStack<T> implements Stack<T> {
    
    private Object[] elem;
    private final static int INIT_SIZE = 10;
    private int top;
    
    public ArrayStack() {
        elem = new Object[INIT_SIZE];
        top = 0;
    }
    
    public ArrayStack(int initSize) {
        elem = new Object[initSize];
        top = 0;
    }

    @Override
    public void clear() {
        top = 0;
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if(!isEmpty()) {
            return (T)elem[top-1];
        }
        return null;
    }

    @Override
    public boolean push(T e) {
        if(top >= elem.length) return false;
        elem[top++] = e;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T pop() {
        if(isEmpty()) return null;
        return (T)elem[--top];
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("[>");
        else {
            sb.append("[");
            for(int i=0; i<top; i++) {
                sb.append(elem[i] + ", ");
            }
            System.out.println(sb.substring(0, sb.lastIndexOf(", ")) + ">");
        }
    }

}
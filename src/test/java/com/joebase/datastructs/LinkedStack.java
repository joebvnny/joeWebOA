package com.joebase.datastructs;

public class LinkedStack<T> implements Stack<T> {
    
    private LinkNode top;
    private int size;

    @Override
    public void clear() {
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if(!isEmpty()) {
            return (T)top.getData();
        }
        return null;
    }

    @Override
    public boolean push(T e) {
        LinkNode p = new LinkNode(e);
        p.setNext(top);
        top = p;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T pop() {
        if(isEmpty()) return null;
        LinkNode p = top;
        top = top.getNext();
        return (T)p.getData();
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("<]");
        else {
            LinkNode node = top;
            sb.append("<");
            while(node != null) {
                sb.append(node.getData() + "—");
                node = node.getNext();
            }
            System.out.println(sb.substring(0, sb.lastIndexOf("—")) + "]");
        }
    }

}
package com.joebase.datastructs;

public class LinkedQueue<T> implements Queue<T> {
    
    private LinkNode head;
    private LinkNode tail;
    private int size;

    public LinkedQueue() {
        head = tail = null;
    }

    @Override
    public void clear() {
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if(head != null) return (T)head.getData();
        return null;
    }

    @Override
    public boolean offer(T e) {
        LinkNode p = new LinkNode(e);
        if(!isEmpty()) {
            tail.setNext(p);
            tail = p;
        } else {
            head = tail = p;
        }
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T poll() {
        if(!isEmpty()) {
            LinkNode p = head;
            head = head.getNext();
            size--;
            return (T)p.getData();
        } else {
            return null;
        }
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("<<");
        else {
            LinkNode node = head;
            sb.append("<");
            while(node != null) {
                sb.append(node.getData() + "—");
                node = node.getNext();
            }
            System.out.println(sb.substring(0, sb.lastIndexOf("—")) + "<");
        }
    }

}
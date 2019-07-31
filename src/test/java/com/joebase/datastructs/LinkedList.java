package com.joebase.datastructs;

import java.util.Scanner;

public class LinkedList<T> implements List<T> {
    
    private LinkNode head;
    private int size;
    
    public LinkedList() {
        head = new LinkNode();
        size = 0;
    }
    
    public LinkedList(int n, boolean order) {
        this();
        if(order) create1(n);
        else create2(n);
    }

    @SuppressWarnings("unchecked")
    private void create1(int n) {
        Scanner sc = new Scanner(System.in);
        for(int j=0; j<n; j++) {
            insert(1, (T)sc.next());
        }
        size = n;
        sc.close();
    }

    @SuppressWarnings("unchecked")
    private void create2(int n) {
        Scanner sc = new Scanner(System.in);
        for(int j=0; j<n; j++) {
            insert(size(), (T)sc.next());
        }
        size = n;
        sc.close();
    }

    @Override
    public void clear() {
        head.setData(null);
        head.setNext(null);
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head.getNext()==null;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int i) {
        if(i<0 || i>size-1) return null;
        LinkNode p = head.getNext();
        for(int j=0; p!=null && j<i; j++) {
            p = p.getNext();
        }
        return (T)p.getData();
    }

    @Override
    public boolean insert(int i, T e) {
        if(i<0 || i>size) return false;
        LinkNode p = head;
        for(int j=0; p!=null && j<i; j++) {
            p = p.getNext();
        }
        LinkNode s = new LinkNode(e);
        s.setNext(p.getNext());
        p.setNext(s);
        size++;
        return true;
    }

    @Override
    public boolean remove(int i) {
        if(i<0 || i>size-1) return false;
        LinkNode p = head;
        for(int j=0; p!=null && j<i; j++) {
            p = p.getNext();
        }
        p.setNext(p.getNext().getNext());
        size--;
        return true;
    }

    @Override
    public boolean update(int i, T x) {
        if(i<0 || i>size-1) return false;
        LinkNode p = head;
        for(int j=0; p!=null && j<=i; j++) {
            p = p.getNext();
        }
        p.setData(x);
        return true;
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("<>");
        else {
            LinkNode node = head.getNext();
            sb.append("<");
            while(node != null) {
                sb.append(node.getData() + "—");
                node = node.getNext();
            }
            System.out.println(sb.substring(0, sb.lastIndexOf("—")) + ">");
        }
    }

}
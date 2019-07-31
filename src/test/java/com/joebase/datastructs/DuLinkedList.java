package com.joebase.datastructs;

import java.util.Scanner;

public class DuLinkedList<T> implements List<T> {
    
    private DuLinkNode head;
    private int size;
    
    public DuLinkedList() {
        head = new DuLinkNode();
        head.setPrev(head);
        head.setNext(head);
    }
    
    @SuppressWarnings("unchecked")
    public DuLinkedList(int n) {
        this();
        Scanner sc = new Scanner(System.in);
        for(int j=0; j<n; j++) {
            insert(0, (T)sc.next());
        }
        sc.close();
    }

    @Override
    public void clear() {
        head.setData(null);
        head.setPrev(null);
        head.setNext(null);
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return head.getPrev()==head.getNext();
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int i) {
        if(i<0 || i>size-1) return null;
        DuLinkNode p = head.getNext();
        for(int j=0; p!=head && j<i; j++) {
            p = p.getNext();
        }
        return (T)p.getData();
    }

    @Override
    public boolean insert(int i, T e) {
        if(i<0 || i>size) return false;
        DuLinkNode p = head;
        for(int j=0; p.getNext()!=head && j<i; j++) {
            p = p.getNext();
        }
        DuLinkNode s = new DuLinkNode(e);
        s.setNext(p.getNext());
        p.getNext().setPrev(s);
        s.setPrev(p);
        p.setNext(s);
        size++;
        return true;
    }

    @Override
    public boolean remove(int i) {
        if(i<0 || i>size-1) return false;
        DuLinkNode p = head;
        for(int j=0; p.getNext()!=head && j<=i; j++) {
            p = p.getNext();
        }
        p.getPrev().setNext(p.getNext());
        p.getNext().setPrev(p.getPrev());
//        p.setNext(p.getNext().getNext());
//        p.getNext().getNext().setPrev(p);
        size--;
        return true;
    }

    @Override
    public boolean update(int i, T x) {
        if(i<0 || i>size-1) return false;
        DuLinkNode p = head;
        for(int j=0; p.getNext()!=head && j<=i; j++) {
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
            DuLinkNode node = head.getNext();
            sb.append("<");
            while(node != head) {
                sb.append(node.getData() + "＝");
                node = node.getNext();
            }
            System.out.println(sb.substring(0, sb.lastIndexOf("＝")) + ">");
        }
    }

}
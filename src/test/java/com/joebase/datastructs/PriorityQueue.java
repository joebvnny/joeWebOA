package com.joebase.datastructs;

public class PriorityQueue<T> implements Queue<T> {
    
    private LinkNode head;
    private LinkNode tail;
    private int size;

    public PriorityQueue() {
        head = tail = null;
    }

    @Override
    public void clear() {
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return head==null;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        if(!isEmpty()) return((T)head.getData());
        else return(null);
    }

    @Override
    public boolean offer(T e) {
        PriorityData pd = (PriorityData)e;
        LinkNode n = new LinkNode(pd);
        if(isEmpty()) head=tail=n;
        else {
            LinkNode p = head, q = head;
            while(p!=null && pd.getPriority()>=((PriorityData)p.getData()).getPriority()) {
                q = p;
                p = p.getNext();
            }
            if(p == null) {
                tail.setNext(n);
                tail = n;
            } else if(p == head) {
                n.setNext(head);
                head = n;
            } else {
                q.setNext(n);
                n.setNext(p);
            }
        }
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T poll() {
        if(!isEmpty()) {
            PriorityData p = (PriorityData)head.getData();
            head = head.getNext();
            return (T)p;
        }
        else return null;
    }

    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        if(isEmpty()) System.out.println("<<");
        else {
            LinkNode node = head;
            sb.append("<");
            while(node != tail.getNext()) {
                PriorityData pd = (PriorityData)node.getData();
                sb.append(pd.getData() + "(" + pd.getPriority() + ")" + "—");
                node = node.getNext();
            }
            System.out.println(sb.substring(0, sb.lastIndexOf("—")) + "<");
        }
    }

}
package com.joebase.datastructs;

public class DuLinkNode {

    private Object data;
    private DuLinkNode prev;
    private DuLinkNode next;
    
    public DuLinkNode() {
        this(null);
    }
    
    public DuLinkNode(Object data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public DuLinkNode getPrev() {
        return prev;
    }
    
    public void setPrev(DuLinkNode prev) {
        this.prev = prev;
    }
    
    public DuLinkNode getNext() {
        return next;
    }
    
    public void setNext(DuLinkNode next) {
        this.next = next;
    }
    
}
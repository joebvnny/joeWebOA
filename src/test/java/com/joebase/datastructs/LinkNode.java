package com.joebase.datastructs;

public class LinkNode {

    private Object data;
    private LinkNode next;
    
    public LinkNode() {
        this(null, null);
    }
    
    public LinkNode(Object data) {
        this(data, null);
    }

    public LinkNode(Object data, LinkNode next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LinkNode getNext() {
        return next;
    }

    public void setNext(LinkNode next) {
        this.next = next;
    }
    
}

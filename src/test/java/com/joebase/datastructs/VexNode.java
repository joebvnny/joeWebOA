package com.joebase.datastructs;

public class VexNode {

    private Object data; // 顶点信息

    private ArcNode firstArc; // 指向第一条依附于该顶点的弧

    public VexNode() {
        this(null, null);
    }

    public VexNode(Object data) {
        this(data, null);
    }

    public VexNode(Object data, ArcNode firstArc) {
        this.data = data;
        this.firstArc = firstArc;
    }

    public Object getData() {
        return data;
    }

    public ArcNode getFirstArc() {
        return firstArc;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setFirstArc(ArcNode firstArc) {
        this.firstArc = firstArc;
    }

}
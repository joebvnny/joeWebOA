package com.joebase.datastructs;

public class PriorityData {

    private Object data;
    private int priority;

    public PriorityData(Object data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}

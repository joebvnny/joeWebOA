package com.joebase.thread;

import java.io.Serializable;

public class Resource implements Serializable {

    private static final long serialVersionUID = 8451416078249449166L;
    
    private String resName;

    public Resource(String resName) {
        super();
        this.resName = resName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    @Override
    public String toString() {
        return "resource[" + resName + "]";
    }
}

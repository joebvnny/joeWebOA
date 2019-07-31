package com.joebunny.common;

/**
 * 通用返给前端的应答对象
 */
@SuppressWarnings("serial")
public class RespBean extends BaseBean {
    public final static String MSG_SUCCESS = "SUCCESS";
    public final static String MSG_FAILURE = "FAILURE";
    
    protected boolean success = false;
    protected String msg;
    protected Object data;

    public RespBean() {
        super();
    }
    public RespBean(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }
    public RespBean setValues(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        return this;
    }
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    
}
package com.joebunny.entity.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返给页面[树]组件的树节点
 */
public class TreeNode {
    
    private Serializable id;
    private String text;
    private String url;
    private String iconCls;
    private Integer seq;
    private String state;
    private Boolean checked;
    private Serializable pid;
    private String ptext;
    private List<TreeNode> children = new ArrayList<TreeNode>(0);
    private Map<String, Object> attributes = new HashMap<String, Object>(0);
    
    public TreeNode() {
        super();
    }
    public TreeNode(Serializable id, String text, Serializable pid) {
        this.id = id;
        this.text = text;
        this.pid = pid;
    }
    public TreeNode(Serializable id, String text, String url, String iconCls, Integer seq, String state, Boolean checked, Serializable pid, String ptext, List<TreeNode> children, Map<String, Object> attributes) {
        super();
        this.id = id;
        this.text = text;
        this.url = url;
        this.iconCls = iconCls;
        this.seq = seq;
        this.state = state;
        this.checked = checked;
        this.pid = pid;
        this.ptext = ptext;
        this.children = children;
        this.attributes = attributes;
    }
    
    public Serializable getId() {
        return id;
    }
    public void setId(Serializable id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    
    public Serializable getPid() {
        return pid;
    }
    public void setPid(Serializable pid) {
        this.pid = pid;
    }
    
    public String getPtext() {
        return ptext;
    }
    public void setPtext(String ptext) {
        this.ptext = ptext;
    }
    
    public List<TreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
    
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
}
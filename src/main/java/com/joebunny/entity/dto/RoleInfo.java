package com.joebunny.entity.dto;

/**
 * 页面需要的角色信息
 */
public class RoleInfo {
    
    private String id;
    private String rolename;
    private String funcIds;
    private String funcNames;
    
    public RoleInfo() {
        super();
    }
    public RoleInfo(String id, String rolename, String funcIds, String funcNames) {
        this.id = id;
        this.rolename = rolename;
        this.funcIds = funcIds;
        this.funcNames = funcNames;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
    
    public String getFuncIds() {
        return funcIds;
    }
    public void setFuncIds(String funcIds) {
        this.funcIds = funcIds;
    }
    
    public String getFuncNames() {
        return funcNames;
    }
    public void setFuncNames(String funcNames) {
        this.funcNames = funcNames;
    }
    
}
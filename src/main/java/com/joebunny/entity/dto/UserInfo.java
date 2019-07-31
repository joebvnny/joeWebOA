package com.joebunny.entity.dto;

import com.joebunny.common.BaseBean;

/**
 * 用户信息（用于会话、缓存和页面）
 */
@SuppressWarnings("serial")
public class UserInfo extends BaseBean {
    
    private String userId;
    private String username;
    private String password;
    private String clientIp;
    private String loginTime;
    private String birthday = "";
    private String gender = "";
    private String deptId = "";
    private String deptName = "";
    private String roleIds = "";
    private String roleNames = "";
    private String funcIds = "";
    private String funcNames = "";
    private String funcUrls = "";
    
    public UserInfo() {
        super();
    }
    public UserInfo(String userId, String username, String password, String clientIp, String loginTime, String birthday, String gender, String deptId, String deptName, String roleIds, String roleNames, String funcIds, String funcNames, String funcUrls) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.clientIp = clientIp;
        this.loginTime = loginTime;
        this.birthday = birthday;
        this.gender = gender;
        this.deptId = deptId;
        this.deptName = deptName;
        this.roleIds = roleIds;
        this.roleNames = roleNames;
        this.funcIds = funcIds;
        this.funcNames = funcNames;
        this.funcUrls = funcUrls;
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getClientIp() {
        return clientIp;
    }
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    
    public String getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
    
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public String getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    
    public String getRoleNames() {
        return roleNames;
    }
    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
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
    
    public String getFuncUrls() {
        return funcUrls;
    }
    public void setFuncUrls(String funcUrls) {
        this.funcUrls = funcUrls;
    }
    
}
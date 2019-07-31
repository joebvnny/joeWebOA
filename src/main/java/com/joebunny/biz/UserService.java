package com.joebunny.biz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joebunny.common.CommonUtils;
import com.joebunny.common.Encryptor;
import com.joebunny.common.RespBean;
import com.joebunny.dao.DeptDao;
import com.joebunny.dao.RoleDao;
import com.joebunny.dao.UserDao;
import com.joebunny.dao.UserInfoCache;
import com.joebunny.entity.Dept;
import com.joebunny.entity.Func;
import com.joebunny.entity.Role;
import com.joebunny.entity.User;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.UserInfo;

/**
 * 用户业务服务
 */
@Service("userService")
public class UserService {
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private DeptDao deptDao;
    private UserInfoCache userInfoCache;
    @Autowired
    public UserService(UserInfoCache userInfoCache) {
        this.userInfoCache = userInfoCache;
        this.userInfoCache.setIdFieldName("userId");
    }
    
    /**
     * 获取在线用户信息
     * @param pager     分页及排序
     * @return DataGrid
     */
    public DataGrid listOnlineUsers(Pager pager) {
        int total = userInfoCache.count();
        List<UserInfo> userInfos = userInfoCache.list(pager.setTotalRows(total));
        DataGrid data = new DataGrid(total, userInfos);
        return data;
    }
    
    /**
     * 用户登录：验证用户名密码
     * @param userInfo  前端传的用户信息
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public RespBean userLogin(UserInfo userInfo) {
        RespBean resp = new RespBean();
        User user = checkUser(userInfo);
        if(user != null) {
            userInfo.setUserId(user.getId()+"");
            userInfo.setPassword(user.getPassword());
            userInfo.setLoginTime(CommonUtils.formatDateTime(new Date(), null));
            userInfoCache.insert(userInfo, 0); // 缓存用户登录信息
            
            StringBuilder funcUrls = new StringBuilder();
            Set<Role> roles = user.getRoles();
            if(roles != null && roles.size() > 0) {
                for(Role role : user.getRoles()) {
                    Set<Func> funcs = role.getFuncs();
                    if(funcs != null && funcs.size() > 0) {
                        for(Func func : funcs) {
                            funcUrls.append(func.getUrl() + ",");
                        }
                    }
                }
            }
            userInfo.setFuncUrls(funcUrls.toString());
            
            resp.setValues(true, "登录成功！", userInfo);
        } else {
            resp.setValues(false, "登录失败！", null);
        }
        return resp;
    }
    
    /**
     * 用户登出：清除缓存信息
     * @param userInfo  用户信息
     */
    public void userLogout(UserInfo userInfo) {
        userInfoCache.delete(userInfo);
    }
    
    /**
     * 通过用户名和密码来验证用户
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public User checkUser(UserInfo userInfo) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("username", userInfo.getUsername());
        String thePassword = userInfo.getPassword();
        paraMap.put("password", Encryptor.shaEncrypt(thePassword));
        User theUser = userDao.select(paraMap, true);
        if(theUser != null) {
            theUser.setPassword(thePassword);
        }
        return theUser;
    }
    
    /**
     * 用户信息列表
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<User> listUsers(Pager pager, boolean cacheable) {
        List<User> users = userDao.list(pager, cacheable);
        return users;
    }
    
    /**
     * 更改用户密码
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public void modifyUserPwd(UserInfo userInfo) {
        String password = userInfo.getPassword();
        User user = userDao.select(Integer.parseInt(userInfo.getUserId()));
        user.setPassword(Encryptor.shaEncrypt(password));
        
        String cacheKey = userInfoCache.getCacheKey(userInfo);
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("password", password);
        userInfoCache.update(cacheKey, paraMap);
    }
    
    /**
     * 用户信息列表
     * @param pager     分页及排序
     * @param cacheable 是否缓存结果
     * @return DataGrid
     * @throws Exception 
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public DataGrid userGrid(Pager pager, boolean cacheable) throws Exception {
        int total = userDao.count(null, null, cacheable);
        pager.setTotalRows(total);
        List<User> users = userDao.list(pager, cacheable);
        
        List<UserInfo> usersInfos = new ArrayList<UserInfo>();
        if(users != null && users.size() > 0) {
            for(User user : users) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(user.getId()+"");
                userInfo.setUsername(user.getUsername());
                userInfo.setPassword(user.getPassword());
                userInfo.setBirthday(CommonUtils.formatDateTime(user.getBirthday(), "yyyy-MM-dd"));
                userInfo.setGender(user.getGender().toString());
                userInfo.setDeptId(user.getDept().getId()+"");
                userInfo.setDeptName(user.getDept().getDeptname());
                Set<Role> roles = user.getRoles();
                if(roles != null && !roles.isEmpty()) {
                    String roleIds = "";
                    String roleNames = "";
                    for(Role role : roles) {
                        if(roleIds.length() > 0) {
                            roleIds += ",";
                            roleNames += ",";
                        }
                        roleIds += role.getId();
                        roleNames += role.getRolename();
                    }
                    userInfo.setRoleIds(roleIds);
                    userInfo.setRoleNames(roleNames);
                }
                usersInfos.add(userInfo);
            }
        }
        
        DataGrid data = new DataGrid(total, usersInfos);
        return data;
    }
    
    /**
     * 增加或更新用户
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean insertOrUpdateUser(UserInfo node) throws Exception {
        RespBean resp = new RespBean();
        Integer id = null;
        if(StringUtils.isNotEmpty(node.getUserId())) {
            id = Integer.parseInt(node.getUserId().toString());
        }
        
        User user = new User();
        user.setId(id);
        user.setUsername(node.getUsername());
        user.setGender(node.getGender().charAt(0));
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(node.getBirthday()));
        
        String did = node.getDeptId();
        if(StringUtils.isNotEmpty(did)) {
            Integer deptId = Integer.parseInt(did);
            Dept dept = deptDao.select(deptId);
            user.setDept(dept);
        }
        
        String rids = node.getRoleIds();
        if(StringUtils.isNotEmpty(rids)) {
            Set<Role> roles = new HashSet<Role>();
            for(String rid : rids.split(",")) {
                Integer roleId = Integer.parseInt(rid);
                Role role = roleDao.select(roleId);
                roles.add(role);
            }
            user.setRoles(roles);
        }
        
        if(id == null) { //新增
            user.setPassword(Encryptor.shaEncrypt("12345"));
            try {
                userDao.insert(user);
                resp.setValues(true, "增加用户["+user.getUsername()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "增加用户["+user.getUsername()+"]记录失败！"+e.getMessage(), null);
            }
        } else { //修改
            user.setPassword(node.getPassword());
            try {
                userDao.update(user);
                resp.setValues(true, "修改用户["+user.getUsername()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "修改用户["+user.getUsername()+"]记录失败！"+e.getMessage(), null);
            }
        }
        
        return resp;
    }
    
    /**
     * 删除用户
     * @param sids  待删用户ID数组
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean deleteUser(Serializable[] sids) {
        RespBean resp = new RespBean();
        try {
            userDao.delete(sids);
            resp.setValues(true, "删除用户"+Arrays.toString(sids)+"记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "删除用户"+Arrays.toString(sids)+"记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
}
package com.joebunny.biz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joebunny.common.RespBean;
import com.joebunny.dao.FuncDao;
import com.joebunny.dao.RoleDao;
import com.joebunny.entity.Func;
import com.joebunny.entity.Role;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.RoleInfo;

/**
 * 角色业务服务
 */
@Service("roleService")
public class RoleService {
    
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FuncDao funcDao;
    
    /**
     * 角色信息列表
     * @param pager     分页及排序
     * @param cacheable 是否缓存结果
     * @return DataGrid
     * @throws Exception 
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public DataGrid roleGrid(Pager pager, boolean cacheable) throws Exception {
        int total = roleDao.count(null, null, cacheable);
        pager.setTotalRows(total);
        List<Role> roles = roleDao.list(pager, cacheable);
        
        List<RoleInfo> rolesInfos = new ArrayList<RoleInfo>();
        if(roles != null && roles.size() > 0) {
            for(Role role : roles) {
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setId(role.getId()+"");
                roleInfo.setRolename(role.getRolename());
                Set<Func> funcs = role.getFuncs();
                if(funcs != null && !funcs.isEmpty()) {
                    String funcIds = "";
                    String funcNames = "";
                    for(Func func : funcs) {
                        if(funcIds.length() > 0) {
                            funcIds += ",";
                            funcNames += ",";
                        }
                        funcIds += func.getId();
                        funcNames += func.getText();
                    }
                    roleInfo.setFuncIds(funcIds);
                    roleInfo.setFuncNames(funcNames);
                }
                rolesInfos.add(roleInfo);
            }
        }
        
        DataGrid data = new DataGrid(total, rolesInfos);
        return data;
    }
    
    /**
     * 增加或更新角色
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean insertOrUpdateRole(RoleInfo node) {
        RespBean resp = new RespBean();
        Integer id = null;
        if(StringUtils.isNotEmpty(node.getId())) {
            id = Integer.parseInt(node.getId().toString());
        }
        
        Role role = new Role();
        role.setId(id);
        role.setRolename(node.getRolename());
        String fids = node.getFuncIds();
        if(StringUtils.isNotEmpty(fids)) {
            Set<Func> funcs = new HashSet<Func>();
            for(String fid : fids.split(",")) {
                Integer funcId = Integer.parseInt(fid);
                Func func = funcDao.select(funcId);
                funcs.add(func);
            }
            role.setFuncs(funcs);
        }
        
        if(id == null) { //新增
            try {
                roleDao.insert(role);
                resp.setValues(true, "增加角色["+role.getRolename()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "增加角色["+role.getRolename()+"]记录失败！"+e.getMessage(), null);
            }
        } else { //修改
            try {
                roleDao.update(role);
                resp.setValues(true, "修改角色["+role.getRolename()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "修改角色["+role.getRolename()+"]记录失败！"+e.getMessage(), null);
            }
        }
        
        return resp;
    }
    
    /**
     * 删除角色
     * @param sids  待删角色ID数组
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean deleteRole(Serializable[] sids) {
        RespBean resp = new RespBean();
        try {
            roleDao.delete(sids);
            resp.setValues(true, "删除角色"+Arrays.toString(sids)+"记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "删除角色"+Arrays.toString(sids)+"记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
}
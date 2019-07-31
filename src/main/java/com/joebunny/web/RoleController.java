package com.joebunny.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebunny.biz.RoleService;
import com.joebunny.common.CommonUtils;
import com.joebunny.common.RespBean;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.RoleInfo;

/**
 * 角色业务控制器
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 角色信息列表
     * @param pager     分页及排序
     * @return DataGrid
     */
    @RequestMapping("/roleGrid.do")
    @ResponseBody
    public DataGrid regionGrid(Pager pager, HttpServletRequest request) {
        DataGrid dataGrid = null;
        try {
            dataGrid = roleService.roleGrid(pager, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataGrid;
    }
    
    /**
     * 编辑（增加或修改）角色
     * @param node  前台传递的角色节点
     * @return RespBean
     */
    @RequestMapping("/editRole.do")
    @ResponseBody
    public RespBean editRole(RoleInfo node) {
        RespBean resp = null;
        if(node != null) {
            node.setRolename(CommonUtils.getCNStringFromAjax(node.getRolename()));
            resp = roleService.insertOrUpdateRole(node);
        }
        return resp;
    }
    
    /**
     * 删除角色
     * @param ids    前台传递的待删角色ID数组
     * @return RespBean
     */
    @RequestMapping("/deleteRole.do")
    @ResponseBody
    public RespBean deleteRole(String ids) {
        RespBean resp = null;
        if(ids != null) {
            String[] idArr = ids.split(",");
            Serializable[] sids = new Serializable[idArr.length];
            for(int i=0; i<idArr.length; i++) {
                sids[i] = Integer.parseInt(idArr[i]);
            }
            resp = roleService.deleteRole(sids);
        }
        return resp;
    }
    
}
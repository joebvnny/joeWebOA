package com.joebunny.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebunny.biz.UserService;
import com.joebunny.common.CommonUtils;
import com.joebunny.common.RespBean;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.UserInfo;

import io.swagger.annotations.ApiOperation;

/**
 * 用户业务控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 浏览器关闭事件：缓存处理
     */
    @RequestMapping("/browserCloseEvent.do")
    public void browserCloseEvent(HttpServletRequest request) {
        this.userLogout(request.getSession(false));
    }
    
    /**
     * 获取在线用户信息列表
     */
    @ApiOperation(value="获取在线用户信息列表", notes="此API的notes")
    @RequestMapping("/listOnlineUsers.do")
    @ResponseBody
    public DataGrid listOnlineUsers(Pager pager, HttpServletRequest request) {
        DataGrid dataGrid = userService.listOnlineUsers(pager);
        return dataGrid;
    }
    
    /**
     * 用户登录
     */
    @RequestMapping("/userLogin.do")
    @ResponseBody
    public RespBean userLogin(UserInfo userInfo, HttpServletRequest request) {
        userInfo.setClientIp(CommonUtils.getClientIp(request));
        RespBean resp = userService.userLogin(userInfo);
        request.getSession().setAttribute("userInfo", (UserInfo)resp.getData());
        return resp;
    }
    
    /**
     * 用户登出
     */
    @RequestMapping("/userLogout.do")
    @ResponseBody
    public RespBean userLogout(HttpSession session) {
        RespBean resp = new RespBean();
        if(session != null) {
            UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
            userService.userLogout(userInfo);
            session.removeAttribute("userInfo");
            session.invalidate();
            resp.setValues(true, "登出成功！", null);
        } else {
            resp.setValues(false, "登出失败！", null);
        }
        return resp;
    }
    
    /**
     * 返回用户更改密码页
     * @return  modifyUserPwd.jsp
     */
    @RequestMapping("/modifyPwdInfo.do")
    public String modifyPwdInfo() {
        return "/admin/modifyUserPwd.jsp";
    }
    
    /**
     * 更改用户密码
     */
    @RequestMapping("/modifyUserPwd.do")
    @ResponseBody
    public RespBean modifyUserPwd(UserInfo userInfo, HttpSession session) {
        RespBean resp = new RespBean(true, "密码更改成功！",null);
        try {
            userService.modifyUserPwd(userInfo);
            session.setAttribute("userInfo", userInfo);
        } catch(Exception e) {
            resp.setValues(false, "密码更改失败！", null);
        }
        return resp;
    }
    
    /**
     * 用户信息列表
     * @param pager     分页及排序
     * @return DataGrid
     */
    @RequestMapping("/userGrid.do")
    @ResponseBody
    public DataGrid regionGrid(Pager pager, HttpServletRequest request) {
        DataGrid dataGrid = null;
        try {
            dataGrid = userService.userGrid(pager, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataGrid;
    }
    
    /**
     * 编辑（增加或修改）用户
     * @param node  前台传递的用户节点
     * @return RespBean
     */
    @RequestMapping("/editUser.do")
    @ResponseBody
    public RespBean editUser(UserInfo node) {
        RespBean resp = null;
        if(node != null) {
            node.setUsername(CommonUtils.getCNStringFromAjax(node.getUsername()));
            try {
                resp = userService.insertOrUpdateUser(node);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return resp;
    }
    
    /**
     * 删除用户
     * @param ids    前台传递的待删用户ID数组
     * @return RespBean
     */
    @RequestMapping("/deleteUser.do")
    @ResponseBody
    public RespBean deleteUser(String ids) {
        RespBean resp = null;
        if(ids != null) {
            String[] idArr = ids.split(",");
            Serializable[] sids = new Serializable[idArr.length];
            for(int i=0; i<idArr.length; i++) {
                sids[i] = Integer.parseInt(idArr[i]);
            }
            resp = userService.deleteUser(sids);
        }
        return resp;
    }
    
}
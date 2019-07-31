package com.joebunny.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebunny.biz.MenuService;
import com.joebunny.common.CommonUtils;
import com.joebunny.common.RespBean;
import com.joebunny.entity.dto.TreeNode;

/**
 * 菜单业务控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    /**
     * 返回前端需要的菜单树格式
     * @return  List<TreeNode>  单根列表
     */
    @RequestMapping("/listMenuTree.do")
    @ResponseBody
    public List<TreeNode> listMenuTree() {
        List<TreeNode> tree = new ArrayList<TreeNode>(0);
        Map<TreeNode, List<TreeNode>> treeMap = menuService.getMenuTreeMap();
        
        TreeNode root = treeMap.keySet().iterator().next();
        List<TreeNode> children = treeMap.get(root);
        root = CommonUtils.generateTree(root, children);
        
        tree.add(root);
        return tree;
    }
    
    /**
     * 返回前端需要的菜单树列表格式
     * @return  List<TreeNode>
     */
    @RequestMapping("/listMenuItem.do")
    @ResponseBody
    public List<TreeNode> listMenuItem() {
        List<TreeNode> menuTree = menuService.getMenuItems();
        return menuTree;
    }
    
    /**
     * 编辑（增加或修改）菜单
     * @param node  前台传递的菜单节点
     * @return RespBean
     */
    @RequestMapping("/editMenu.do")
    @ResponseBody
    public RespBean editMenu(TreeNode node) {
        RespBean resp = null;
        if(node != null) {
            node.setText(CommonUtils.getCNStringFromAjax(node.getText()));
            resp = menuService.appendOrUpdateMenu(node);
        }
        return resp;
    }
    
    /**
     * 删除菜单
     * @param ids    前台传递的待删菜单ID数组
     * @return RespBean
     */
    @RequestMapping("/deleteMenu.do")
    @ResponseBody
    public RespBean deleteMenu(String ids) {
        RespBean resp = null;
        if(ids != null) {
            String[] idArr = ids.split(",");
            Serializable[] sids = new Serializable[idArr.length];
            for(int i=0; i<idArr.length; i++) {
                sids[i] = Integer.parseInt(idArr[i]);
            }
            resp = menuService.deleteMenu(sids);
        }
        return resp;
    }
    
}
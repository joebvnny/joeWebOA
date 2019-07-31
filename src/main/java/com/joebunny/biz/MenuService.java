package com.joebunny.biz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joebunny.common.RespBean;
import com.joebunny.dao.MenuDao;
import com.joebunny.entity.Menu;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.TreeNode;

/**
 * 菜单业务服务
 */
@Service("menuService")
public class MenuService {
    
    @Autowired
    private MenuDao menuDao;
    
    /**
     * 获取所有菜单条目，按Map<根节点, 子孙节点列表>返回
     * @return  Map<TreeNode, List<TreeNode>>
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public Map<TreeNode, List<TreeNode>> getMenuTreeMap() {
        TreeNode treeRoot = null;
        List<TreeNode> treeNodes = new ArrayList<TreeNode>(0);
        Map<TreeNode, List<TreeNode>> treeMap = new HashMap<TreeNode, List<TreeNode>>(0);
        
        Pager pager = new Pager("seq", "asc");
        List<Menu> menuItems = menuDao.list(pager, true);
        
        if(menuItems != null && menuItems.size() > 0) {
            for(Menu menuItem : menuItems) {
                TreeNode treeNode = new TreeNode();
                BeanUtils.copyProperties(menuItem, treeNode, "parent", "children");
                Menu parent = menuItem.getParent();
                if(parent == null) {
                    treeRoot = treeNode;
                    continue;
                } else {
                    treeNode.setPid(parent.getId());
                    treeNode.setPtext(parent.getText());
                }
                treeNodes.add(treeNode);
            }
        }
        
        treeMap.put(treeRoot, treeNodes);
        return treeMap;
    }
    
    /**
     * 获取所有菜单条目
     * @return  List<TreeNode>
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<TreeNode> getMenuItems() {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>(0);
        
        Pager pager = new Pager("seq", "asc");
        List<Menu> menuItems = menuDao.list(pager, true);
        
        if(menuItems != null && menuItems.size() > 0) {
            for(Menu menuItem : menuItems) {
                TreeNode treeNode = new TreeNode();
                BeanUtils.copyProperties(menuItem, treeNode, "parent", "children");
                Menu parent = menuItem.getParent();
                if(parent != null) {
                    treeNode.setPid(parent.getId());
                    treeNode.setPtext(parent.getText());
                }
                treeNodes.add(treeNode);
            }
        }
        
        return treeNodes;
    }
    
    /**
     * 增加或更新菜单项
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean appendOrUpdateMenu(TreeNode node) {
        RespBean resp = new RespBean();
        
        Integer id = Integer.parseInt(node.getId().toString());
        Menu menu = new Menu();
        menu.setId(id);
        BeanUtils.copyProperties(node, menu, "id", "children");
        if(node.getPid() != null) {
            Menu pmenu = menuDao.select(Integer.parseInt(node.getPid().toString()));
            menu.setParent(pmenu);
        }
        if(id == 0) { //新增
            try {
                menuDao.insert(menu);
                resp.setValues(true, "增加菜单["+menu.getText()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "增加菜单["+menu.getText()+"]记录失败！"+e.getMessage(), null);
            }
        } else { //修改
            try {
                menuDao.update(menu);
                resp.setValues(true, "修改菜单["+menu.getText()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "修改菜单["+menu.getText()+"]记录失败！"+e.getMessage(), null);
            }
        }
        
        return resp;
    }
    
    /**
     * 删除菜单
     * @param sids  待删菜单ID数组
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean deleteMenu(Serializable[] sids) {
        RespBean resp = new RespBean();
        try {
            menuDao.delete(sids);
            resp.setValues(true, "删除菜单"+Arrays.toString(sids)+"记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "删除菜单"+Arrays.toString(sids)+"记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
}
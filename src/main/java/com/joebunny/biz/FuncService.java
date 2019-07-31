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
import com.joebunny.dao.FuncDao;
import com.joebunny.entity.Func;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.TreeNode;

/**
 * 职能业务服务
 */
@Service("funcService")
public class FuncService {
    
    @Autowired
    private FuncDao funcDao;
    
    /**
     * 获取所有职能条目，按Map<根节点, 子孙节点列表>返回
     * @return  Map<TreeNode, List<TreeNode>>
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public Map<TreeNode, List<TreeNode>> getFuncTreeMap() {
        TreeNode treeRoot = null;
        List<TreeNode> treeNodes = new ArrayList<TreeNode>(0);
        Map<TreeNode, List<TreeNode>> treeMap = new HashMap<TreeNode, List<TreeNode>>(0);
        
        Pager pager = new Pager("seq", "asc");
        List<Func> funcItems = funcDao.list(pager, true);
        
        if(funcItems != null && funcItems.size() > 0) {
            for(Func funcItem : funcItems) {
                TreeNode treeNode = new TreeNode();
                BeanUtils.copyProperties(funcItem, treeNode, "parent", "children");
                Func parent = funcItem.getParent();
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
     * 增加或更新职能项
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean appendOrUpdateFunc(TreeNode node) {
        RespBean resp = new RespBean();
        
        Integer id = Integer.parseInt(node.getId().toString());
        Func func = new Func();
        func.setId(id);
        BeanUtils.copyProperties(node, func, "id", "children");
        if(node.getPid() != null) {
            Func pfunc = funcDao.select(Integer.parseInt(node.getPid().toString()));
            func.setParent(pfunc);
        }
        if(id == 0) { //新增
            try {
                funcDao.insert(func);
                resp.setValues(true, "增加职能["+func.getText()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "增加职能["+func.getText()+"]记录失败！"+e.getMessage(), null);
            }
        } else { //修改
            try {
                funcDao.update(func);
                resp.setValues(true, "修改职能["+func.getText()+"]记录成功！", null);
            } catch(Exception e) {
                resp.setValues(false, "修改职能["+func.getText()+"]记录失败！"+e.getMessage(), null);
            }
        }
        
        return resp;
    }
    
    /**
     * 删除职能
     * @param sids  待删职能ID数组
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean deleteFunc(Serializable[] sids) {
        RespBean resp = new RespBean();
        try {
            funcDao.delete(sids);
            resp.setValues(true, "删除职能"+Arrays.toString(sids)+"记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "删除职能"+Arrays.toString(sids)+"记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
}
package com.joebunny.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebunny.biz.FuncService;
import com.joebunny.common.CommonUtils;
import com.joebunny.common.RespBean;
import com.joebunny.entity.dto.TreeNode;

/**
 * 职能业务控制器
 */
@Controller
@RequestMapping("/func")
public class FuncController {
    
    @Autowired
    private FuncService funcService;
    
    /**
     * 返回前端需要的职能树格式
     * @return  List<TreeNode>  单根列表
     */
    @RequestMapping("/listFuncTree.do")
    @ResponseBody
    public List<TreeNode> listFuncTree() {
        List<TreeNode> tree = new ArrayList<TreeNode>(0);
        Map<TreeNode, List<TreeNode>> treeMap = funcService.getFuncTreeMap();
        
        TreeNode root = treeMap.keySet().iterator().next();
        List<TreeNode> children = treeMap.get(root);
        root = CommonUtils.generateTree(root, children);
        
        tree.add(root);
        return tree;
    }
    
    /**
     * 编辑（增加或修改）职能
     * @param node  前台传递的职能节点
     * @return RespBean
     */
    @RequestMapping("/editFunc.do")
    @ResponseBody
    public RespBean editFunc(TreeNode node) {
        RespBean resp = null;
        if(node != null) {
            node.setText(CommonUtils.getCNStringFromAjax(node.getText()));
            resp = funcService.appendOrUpdateFunc(node);
        }
        return resp;
    }
    
    /**
     * 删除职能
     * @param ids    前台传递的待删职能ID数组
     * @return RespBean
     */
    @RequestMapping("/deleteFunc.do")
    @ResponseBody
    public RespBean deleteFunc(String ids) {
        RespBean resp = null;
        if(ids != null) {
            String[] idArr = ids.split(",");
            Serializable[] sids = new Serializable[idArr.length];
            for(int i=0; i<idArr.length; i++) {
                sids[i] = Integer.parseInt(idArr[i]);
            }
            resp = funcService.deleteFunc(sids);
        }
        return resp;
    }
    
}
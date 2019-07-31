package com.joebunny.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.joebunny.biz.RegionService;
import com.joebunny.common.CommonUtils;
import com.joebunny.common.JsonModelAndView;
import com.joebunny.common.RespBean;
import com.joebunny.entity.Region;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;
import com.joebunny.entity.dto.TreeNode;

/**
 * 地区业务控制器
 */
@Controller
@RequestMapping("/region")
public class RegionController {
    
    @Autowired
    private RegionService regionService;
    
    /**
     * 获取地区信息
     * @param request.areaCode  地区编码（NULL:返回所有；NotNULL:返回特定）
     * @return ModelAndView
     */
    @RequestMapping("/listRegion.do")
    public ModelAndView listRegion(HttpServletRequest request) {
        String areaCode = request.getParameter("areaCode");
        RespBean resp = regionService.listRegion(areaCode);
        return new JsonModelAndView(resp);
    }
    
    /**
     * 地区信息列表
     * @param pager     分页及排序
     * @param region    查询条件
     * @return DataGrid
     */
    @RequestMapping("/regionGrid.do")
    @ResponseBody
    public DataGrid regionGrid(Pager pager, Region region, HttpServletRequest request) {
        DataGrid dataGrid = null;
        try {
            dataGrid = regionService.regionGrid(pager, region, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataGrid;
    }
    
    /**
     * 增加地区信息
     * @param region    前台传递的新增地区信息
     * @return RespBean
     */
    @RequestMapping("/insertRegion.do")
    @ResponseBody
    public RespBean insertRegion(Region region) {
        RespBean resp = null;
        if(region != null) {
            region.setName(CommonUtils.getCNStringFromAjax(region.getName()));
            resp = regionService.insertRegion(region);
        }
        return resp;
    }
    
    /**
     * 修改地区信息
     * @param region    前台传递的修改地区信息
     * @return RespBean
     */
    @RequestMapping("/updateRegion.do")
    @ResponseBody
    public RespBean updateRegion(Region region) {
        RespBean resp = null;
        if(region != null) {
            region.setName(CommonUtils.getCNStringFromAjax(region.getName()));
            resp = regionService.updateRegion(region);
        }
        return resp;
    }
    
    /**
     * 删除地区信息
     * @param ids    前台传递的待删地区ID数组
     * @return RespBean
     */
    @RequestMapping("/deleteRegion.do")
    @ResponseBody
    public RespBean deleteRegion(String ids) {
        RespBean resp = null;
        if(ids != null) {
            String[] idArr = ids.split(",");
            Serializable[] sids = new Serializable[idArr.length];
            for(int i=0; i<idArr.length; i++) {
                sids[i] = Integer.parseInt(idArr[i]);
            }
            resp = regionService.deleteRegion(sids);
        }
        return resp;
    }
    
    /**
     * 生成地区异步树
     * @param id    前台传递的树节点ID
     */
    @RequestMapping("/regionTree.do")
    @ResponseBody
    public List<TreeNode> regionTree(String id) {
        List<Region> regions = regionService.getRegionTree(id);
        List<TreeNode> treeNodes = new ArrayList<TreeNode>(0);
        TreeNode treeNode = null;
        if(regions.size() > 0) {
            for(Region region : regions) {
                treeNode = new TreeNode(region.getId(), region.getName(), region.getParentId());
                if(regionService.countChildren(region.getId()) > 0) {
                    treeNode.setState("closed");
                }
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }
    
}
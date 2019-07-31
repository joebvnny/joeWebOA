package com.joebunny.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joebunny.biz.DeptService;
import com.joebunny.entity.Dept;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;

/**
 * 部门业务控制器
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    
    @Autowired
    private DeptService deptService;
    
    /**
     * 部门信息列表
     * @param pager     分页及排序
     * @return DataGrid
     */
    @RequestMapping("/deptGrid.do")
    @ResponseBody
    public DataGrid regionGrid(Pager pager, HttpServletRequest request) {
        DataGrid dataGrid = null;
        try {
            dataGrid = deptService.deptGrid(pager, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataGrid;
    }
    
    @RequestMapping("/listDepts.do")
    @ResponseBody
    public List<Dept> listDepts(Pager pager, HttpServletRequest request) {
        List<Dept> depts = null;
        try {
            depts = deptService.listDepts(pager, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return depts;
    }
    
}
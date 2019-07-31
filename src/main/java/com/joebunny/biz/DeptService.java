package com.joebunny.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joebunny.dao.DeptDao;
import com.joebunny.entity.Dept;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;

/**
 * 部门业务服务
 */
@Service("deptService")
public class DeptService {
    
    @Autowired
    private DeptDao deptDao;
    
    /**
     * 部门信息列表
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<Dept> listDepts(Pager pager, boolean cacheable) {
        List<Dept> depts = deptDao.list(pager, cacheable);
        return depts;
    }
    
    /**
     * 部门信息列表
     * @param pager     分页及排序
     * @param cacheable 是否缓存结果
     * @return DataGrid
     * @throws Exception 
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public DataGrid deptGrid(Pager pager, boolean cacheable) throws Exception {
        int total = deptDao.count(null, null, cacheable);
        pager.setTotalRows(total);
        List<Dept> depts = deptDao.list(pager, cacheable);
        DataGrid data = new DataGrid(total, depts);
        return data;
    }

}
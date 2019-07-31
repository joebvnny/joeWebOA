package com.joebunny.biz;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joebunny.common.CommonUtils;
import com.joebunny.common.RespBean;
import com.joebunny.dao.RegionDao;
import com.joebunny.entity.Region;
import com.joebunny.entity.dto.DataGrid;
import com.joebunny.entity.dto.Pager;

/**
 * 区域业务服务
 */
@Service("regionService")
@SuppressWarnings("unchecked")
public class RegionService {
    
    @Autowired
    private RegionDao regionDao;
    
    /**
     * 获取地区信息
     * @param areaCode  地区编码
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public RespBean listRegion(String areaCode) {
        RespBean resp = new RespBean();
        if(StringUtils.isNotEmpty(areaCode)) {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("areaCode", areaCode);
            Region region = regionDao.select(paraMap, true);
            resp.setValues(true, RespBean.MSG_SUCCESS, region);
        } else {
            List<Region> regions = regionDao.list(null, true);
            resp.setValues(true, RespBean.MSG_SUCCESS, regions);
        }
        return resp;
    }
    
    /**
     * 地区信息列表
     * @param pager     分页及排序
     * @param cacheable 是否缓存结果
     * @return DataGrid
     * @throws Exception 
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public DataGrid regionGrid(Pager pager, Region region, boolean cacheable) throws Exception {
        String sql = null;
        int total = 0;
        List<Region> regions = null;
        Map<String, Object> paraMap = (Map<String, Object>)CommonUtils.obj2Map(region);
        if(region == null || CommonUtils.isObjMapNull(paraMap)) {
            total = regionDao.count(sql, null, cacheable);
            pager.setTotalRows(total);
            regions = regionDao.list(pager, cacheable);
        } else {
            sql = addWhereSQL(region, paraMap);
            total = regionDao.count(sql, paraMap, cacheable);
            pager.setTotalRows(total);
            regions = regionDao.list(sql, paraMap, pager, cacheable);
        }
        DataGrid data = new DataGrid(total, regions);
        return data;
    }
    private String addWhereSQL(Region region, Map<String, Object> paraMap) {
        String sql = "WHERE 1=1";
        if(StringUtils.isNotEmpty(region.getName())) {
            sql += " AND e.name LIKE :name";
            paraMap.put("name", "%"+region.getName()+"%");
        }
        if(StringUtils.isNotEmpty(region.getAreaCode())) {
            sql += " AND e.areaCode LIKE :areaCode";
            paraMap.put("areaCode", region.getAreaCode()+"%");
        }
        return sql;
    }
    
    /**
     * 增加地区信息
     * @param region  地区信息
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean insertRegion(Region region) {
        RespBean resp = new RespBean();
        try {
            regionDao.insert(region);
            resp.setValues(true, "增加地区["+region.getAreaCode()+"]记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "增加地区["+region.getAreaCode()+"]记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
    /**
     * 修改地区信息
     * @param region  地区信息
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean updateRegion(Region region) {
        RespBean resp = new RespBean();
        try {
            Region theRegion = regionDao.select(region.getId());
            BeanUtils.copyProperties(region, theRegion);
            regionDao.update(theRegion);
            resp.setValues(true, "更新地区["+region.getAreaCode()+"]记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "更新地区["+region.getAreaCode()+"]记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
    /**
     * 删除地区信息
     * @param sids  待删地区ID数组
     * @return RespBean
     */
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public RespBean deleteRegion(Serializable[] sids) {
        RespBean resp = new RespBean();
        try {
            regionDao.delete(sids);
            resp.setValues(true, "删除地区"+Arrays.toString(sids)+"记录成功！", null);
        } catch(Exception e) {
            resp.setValues(false, "删除地区"+Arrays.toString(sids)+"记录失败！"+e.getMessage(), null);
        }
        return resp;
    }
    
    /**
     * 根据ID获取地区树节点
     * @param id    地区ID
     * @return  List<Region>
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public List<Region> getRegionTree(String id) {
        String sql = null;
        Map<String, Object> paraMap = new HashMap<String, Object>(0);
        if(StringUtils.isEmpty(id)) {
            sql = "WHERE e.parentId is null";
        } else {
            sql = "WHERE e.parentId=:parentId";
            paraMap.put("parentId", Integer.parseInt(id));
        }
        return regionDao.list(sql, paraMap, null, true);
    }
    
    /**
     * 获取指定ID地区的子节点数
     */
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class)
    public int countChildren(Integer id) {
        return regionDao.count("WHERE e.parentId="+id, null, true);
    }
    
}
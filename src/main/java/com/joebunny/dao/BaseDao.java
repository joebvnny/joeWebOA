package com.joebunny.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.joebunny.entity.dto.Pager;

/**
 * 基础业务数据访问接口
 */
public interface BaseDao<T> {
    
    /**
     * 新增单笔业务数据
     */
    public void insert(T obj);
    
    /**
     * 删除单笔业务数据
     */
    public void delete(T obj);
    
    /**
     * 删除特定ID业务数据
     */
    public void delete(Serializable id);
    
    /**
     * 删除一组ID的业务数据
     */
    public void delete(Serializable[] ids);
    
    /**
     * 更新单笔业务数据
     */
    public void update(T obj);
    
    /**
     * 更新特定ID业务数据
     */
    public void update(Serializable id, Map<String, Object> paraMap);
    
    /**
     * 获取业务数据记录总数
     */
    public int count(String sql, Map<String, Object> paraMap, boolean cacheable);
    
    /**
     * 根据ID查找业务数据
     */
    public T select(Serializable id);
    
    /**
     * 根据属性Map查找业务数据
     */
    public T select(Map<String, Object> paraMap, boolean cacheable);
    
    /**
     * 查找某类所有业务数据<br />
     * 分页为空：整体输出；分页不空：逐页输出
     */
    public List<T> list(Pager pager, boolean cacheable);
    
    /**
     * 根据条件查找某类业务数据<br />
     * 分页为空：整体输出；分页不空：逐页输出
     */
    public List<T> list(String sql, Map<String, Object> paraMap, Pager pager, boolean cacheable);
    
}
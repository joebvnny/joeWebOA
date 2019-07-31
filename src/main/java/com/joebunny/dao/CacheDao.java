package com.joebunny.dao;

import java.util.List;
import java.util.Map;

import com.joebunny.entity.dto.Pager;

/**
 * 基础数据缓存访问接口
 * <br/>
 * 严格约定缓存键命名规则：cacheKey=objTypeName:objIdString
 */
public interface CacheDao<T> {
    
    /**
     * 按约定规则返回对象的缓存键
     */
    public String getCacheKey(T obj);
    
    /**
     * 新增单笔缓存数据：指定存活时间（秒）
     */
    public void insert(T obj, Integer keyTTL);
    
    /**
     * 删除单笔缓存数据
     */
    public void delete(T obj);
    
    /**
     * 更新单笔缓存数据
     */
    public void update(T obj);
    
    /**
     * 根据参数Map更新缓存数据
     */
    public void update(String key, Map<String, String> paraMap);
    
    /**
     * 获取某类缓存数据记录总数
     */
    public int count();
    
    /**
     * 根据KEY查找特定缓存数据
     */
    public T select(String key);
    
    /**
     * 查找某类所有缓存数据<br />
     * 分页为空：整体输出；分页不空：逐页输出
     */
    public List<T> list(Pager pager);
    
}
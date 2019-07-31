package com.joebunny.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.joebunny.common.CommonUtils;
import com.joebunny.entity.dto.Pager;

import redis.clients.jedis.Jedis;

/**
 * 统一数据缓存访问Jedis实现
 */
@SuppressWarnings("unchecked")
public abstract class CacheDaoJedis<T> implements CacheDao<T> {
    
    private static final Logger logger = Logger.getLogger(CacheDaoJedis.class);
    
    protected static final int JEDIS_KEY_TTL = 36000; //默认缓存十小时
    
    //具体缓存数据类型
    protected Class<T> entityType;
    //数据缓存建前缀：cacheKey=[objTypeName:]objIdString
    protected String cacheKeyPrefix;
    //T类型的id属性名
    protected String idFieldName;
    public CacheDaoJedis() {
        this.entityType = ((Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        this.cacheKeyPrefix = entityType.getSimpleName() + ":";
    }
    public CacheDaoJedis(String idFieldName) {
        this.idFieldName = idFieldName;
    }
    /**
     * T类型的id字段名，必设！
     */
    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }
    @Autowired
    private JedisConnectionFactory connectionFactory;
    protected Jedis getSession() {
        return connectionFactory.getShardInfo().createResource();
    }
    
    @Override
    public String getCacheKey(T obj) {
        String cacheKey = null;
        try {
            cacheKey = this.cacheKeyPrefix + CommonUtils.getValueByFieldName(obj, this.idFieldName);
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return cacheKey;
    }
    
    @Override
    public void insert(T obj, Integer keyTTL) {
        Jedis jedis = null;
        if(obj != null && StringUtils.isNotEmpty(idFieldName)) {
            try {
                jedis = this.getSession();
                String key = this.getCacheKey(obj);
                Map<String, String> values = (Map<String, String>)CommonUtils.obj2Map(obj);
                jedis.hmset(key, values);
                if(keyTTL==null || keyTTL<=0) {
                    keyTTL = JEDIS_KEY_TTL;
                }
                jedis.expire(key, keyTTL);
            } catch(Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                if(jedis != null) {
                    jedis.close();
                }
            }
        } else {
            if(StringUtils.isEmpty(idFieldName)) {
                throw new RuntimeException("必须设置idFieldName");
            }
        }
    }
    
    @Override
    public void delete(T obj) {
        Jedis jedis = null;
        if(obj != null && StringUtils.isNotEmpty(idFieldName)) {
            try {
                jedis = this.getSession();
                String key = this.getCacheKey(obj);
                String[] allFields = CommonUtils.getFieldsArray(obj);
                jedis.hdel(key, allFields);
            } catch(Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                if(jedis != null) {
                    jedis.close();
                }
            }
        } else {
            if(StringUtils.isEmpty(idFieldName)) {
                throw new RuntimeException("必须设置idFieldName");
            }
        }
    }
    
    @Override
    public void update(T obj) {
        Jedis jedis = null;
        if(obj != null && StringUtils.isNotEmpty(idFieldName)) {
            try {
                jedis = this.getSession();
                String key = this.getCacheKey(obj);
                Map<String, Object> values = (Map<String, Object>)CommonUtils.obj2Map(obj);
                jedis.hmset(key, CommonUtils.objMap2StrMap(values));
            } catch(Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                if(jedis != null) {
                    jedis.close();
                }
            }
        } else {
            if(StringUtils.isEmpty(idFieldName)) {
                throw new RuntimeException("必须设置idFieldName");
            }
        }
    }
    
    @Override
    public void update(String key, Map<String, String> paraMap) {
        Jedis jedis = null;
        if(StringUtils.isNotEmpty(key) && paraMap.size() > 0) {
            try {
                jedis = this.getSession();
                for(Map.Entry<String, String> entry : paraMap.entrySet()) {
                    jedis.hset(key, entry.getKey(), entry.getValue());
                }
            } catch(Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                jedis.close();
            }
        }
    }
    
    @Override
    public int count() {
        int count = 0;
        Jedis jedis = null;
        try {
            jedis = this.getSession();
            count = jedis.keys(cacheKeyPrefix + "*").size();
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
        return count;
    }
    
    @Override
    public T select(String key) {
        T obj = null;
        if(StringUtils.isNotEmpty(key)) {
            Jedis jedis = null;
            try {
                jedis = this.getSession();
                Map<String, String> objMap = jedis.hgetAll(key);
                obj = CommonUtils.map2Obj(objMap, this.entityType);
            } catch(Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            } finally {
                jedis.close();
            }
        }
        return obj;
    }
    
    @Override
    public List<T> list(Pager pager) {
        List<T> list = new ArrayList<T>(0);
        Jedis jedis = null;
        try {
            jedis = this.getSession();
            Set<String> keys = jedis.keys(cacheKeyPrefix + "*");
            if(keys != null && keys.size() > 0) {
                for(String key : keys) {
                    Map<String, String> objMap = jedis.hgetAll(key);
                    list.add(CommonUtils.map2Obj(objMap, this.entityType));
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
        
        if(pager != null) {
            if(list.size() > 0) {
                Comparator<T> c = (pager.getOrder().equalsIgnoreCase("ASC") ? null : new ReverseComparator());
                BeanComparator<T> bc = new BeanComparator<T>(pager.getSort() ,c);
                Collections.sort(list, bc);
                list = list.subList(pager.getStartRow(), pager.getEndRow()+1);
            }
        }
        
        return list;
    }
    
}
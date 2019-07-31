package com.joebunny.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.joebunny.entity.dto.Pager;

/**
 * 统一业务数据访问Hibernate实现
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoHibernate<T> implements BaseDao<T> {
    
    //具体业务数据类型
    protected Class<T> entityType;
    public BaseDaoHibernate() {
        this.entityType = ((Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
    }
    @Autowired
    private SessionFactory sessionFactory;
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void insert(T obj) {
        this.getSession().save(obj);
    }
    
    @Override
    public void delete(T obj) {
        this.getSession().delete(obj);
    }
    
    @Override
    public void delete(Serializable id) {
        String hql = "DELETE FROM " + entityType.getName() + " e WHERE e.id=:id";
        Query query = this.getSession().createQuery(hql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    @Override
    public void delete(Serializable[] ids) {
        StringBuffer hql = new StringBuffer("DELETE FROM " + entityType.getName() + " e WHERE e.id IN (");
        for(int i=0; i<ids.length; i++) {
            if(i == ids.length-1) {
                hql.append(ids[i] + ")");
            } else {
                hql.append(ids[i] + ",");
            }
        }
        Query query = this.getSession().createQuery(hql.toString());
        query.executeUpdate();
    }
    
    @Override
    public void update(T obj) {
        this.getSession().update(obj);
    }
    
    @Override
    public void update(Serializable id, Map<String, Object> paraMap) {
        String hql = "UPDATE " + entityType.getName() + " e SET ";
        for(Map.Entry<String, Object> paraEntry : paraMap.entrySet()) {
            hql += "e." + paraEntry.getKey() + "=:" + paraEntry.getKey() + ",";
        }
        hql = hql.substring(0, hql.lastIndexOf(","));
        hql = hql + " WHERE e.id=:id";
        Query query = this.getSession().createQuery(hql);
        for(Map.Entry<String, Object> paraEntry : paraMap.entrySet()) {
            query.setParameter(paraEntry.getKey(), paraEntry.getValue());
        }
        query.setParameter("id", id);
        query.executeUpdate();
    }
    
    @Override
    public int count(String sql, Map<String, Object> paraMap, boolean cacheable) {
        String hql = "SELECT count(*) FROM " + entityType.getName() + " e ";
        if(StringUtils.isNotEmpty(sql)) {
            hql = (sql.startsWith("WHERE") || sql.startsWith("where")) ? hql+sql : sql;
        }
        Query query = this.getSession().createQuery(hql).setCacheable(cacheable);
        if(paraMap != null && paraMap.size() > 0) {
            query.setProperties(paraMap);
        }
        long count = (long)query.uniqueResult();
        int retCount = (int)count;
        return retCount;
    }
    
    @Override
    public T select(Serializable id) {
        T obj = (T)this.getSession().get(entityType, id);
        return obj;
    }
    
    @Override
    public T select(Map<String, Object> paraMap, boolean cacheable) {
        String hql = "SELECT e FROM " + entityType.getName() + " e WHERE ";
        for(Map.Entry<String, Object> paraEntry : paraMap.entrySet()) {
            hql += "e." + paraEntry.getKey() + "=:" + paraEntry.getKey() + " AND ";
        }
        hql = hql.substring(0, hql.lastIndexOf(" AND "));
        Query query = this.getSession().createQuery(hql).setCacheable(cacheable);
        for(Map.Entry<String, Object> paraEntry : paraMap.entrySet()) {
            query.setParameter(paraEntry.getKey(), paraEntry.getValue());
        }
        T obj = (T)query.uniqueResult();
        return obj;
    }
    
    @Override
    public List<T> list(Pager pager, boolean cacheable) {
        List<T> list = new ArrayList<T>(0);
        String hql = "SELECT e FROM " + entityType.getName() + " e";
        Query query = this.getSession().createQuery(hql).setCacheable(cacheable);
        if(pager == null) {
            list = query.list();
        } else {
            hql += pager.getOrderBySQL();
            query = this.getSession().createQuery(hql).setCacheable(cacheable);
            if(pager.getStartRow() != null) {
                list = query.setFirstResult(pager.getStartRow()).setMaxResults(pager.getRows()).list();
            } else {
                list = query.list();
            }
        }
        return list;
    }
    
    @Override
    public List<T> list(String sql, Map<String, Object> paraMap, Pager pager, boolean cacheable) {
        List<T> list = new ArrayList<T>(0);
        String hql = "SELECT e FROM " + entityType.getName() + " e ";
        if(StringUtils.isNotEmpty(sql)) {
            hql = (sql.startsWith("WHERE") || sql.startsWith("where")) ? hql+sql : sql;
        }
        Query query = this.getSession().createQuery(hql).setCacheable(cacheable);
        if(paraMap!=null && !paraMap.isEmpty()) {
            query.setProperties(paraMap);
        }
        if(pager == null) {
            list = query.list();
        } else {
            hql += pager.getOrderBySQL();
            query = this.getSession().createQuery(hql).setCacheable(cacheable);
            if(paraMap!=null && !paraMap.isEmpty()) {
                query.setProperties(paraMap);
            }
            if(pager.getStartRow() != null) {
                list = query.setFirstResult(pager.getStartRow()).setMaxResults(pager.getRows()).list();
            } else {
                list = query.list();
            }
        }
        return list;
    }
    
}
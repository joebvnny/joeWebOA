package com.joebunny.common;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.joebunny.entity.dto.UserInfo;

import redis.clients.jedis.Jedis;

public class MySessionListener implements HttpSessionListener {
    
    @Autowired
    private JedisConnectionFactory connectionFactory;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        Jedis jedis = null;
        if(userInfo != null) {
            try {
                jedis = connectionFactory.getShardInfo().createResource();
                String cacheKey = CommonUtils.getCacheKeyByIdField(userInfo, "userId");
                String[] allFields = CommonUtils.getFieldsArray(userInfo);
                jedis.hdel(cacheKey, allFields);
            } catch(Exception e) {
                throw new RuntimeException("销毁session释放缓存时出错：" + e.getMessage());
            } finally {
                if(jedis != null) {
                    jedis.close();
                }
            }
        }
    }
    
}
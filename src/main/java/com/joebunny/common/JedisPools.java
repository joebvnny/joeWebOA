package com.joebunny.common;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis管理Redis连接池
 */
public class JedisPools {
    
    private static final JedisPool pool;
    
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("jedis");
        if(bundle == null) {
            throw new IllegalArgumentException("[jedis.properties] is not found!");
        }
        
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle.getString("jedis.pool.maxTotal")));
        config.setMaxIdle(Integer.valueOf(bundle.getString("jedis.pool.maxIdle")));
        config.setMinIdle(Integer.valueOf(bundle.getString("jedis.pool.minIdle")));
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("jedis.pool.maxWaitMillis")));
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("jedis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.valueOf(bundle.getString("jedis.pool.testOnReturn")));
        
        String hostName = bundle.getString("redis.hostName");
        int port = Integer.valueOf(bundle.getString("redis.port"));
        int timeout = Integer.valueOf(bundle.getString("redis.timeout"));
        String password = bundle.getString("redis.password");
        pool = new JedisPool(config, hostName, port, timeout, password);
    }
    
    public static void releasePool() {
        pool.close();
    }
    
    public static Jedis getJedis() {
        return pool.getResource();
    }
    
    public static void closeJedis(Jedis jedis) {
        jedis.close();
    }
    
}
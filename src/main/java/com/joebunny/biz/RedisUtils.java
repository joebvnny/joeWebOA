package com.joebunny.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

@Component
public class RedisUtils {

    @Autowired
    private JedisConnectionFactory connectionFactory;

	// 获取redis 服务器信息
	public String getRedisInfo() {

		Jedis jedis = null;
		try {
			jedis = connectionFactory.getShardInfo().createResource();
			Client client = jedis.getClient();
			client.info();
			String info = client.getBulkReply();
			return info;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取日志列表
	public List<Slowlog> getLogs(long entries) {
		Jedis jedis = null;
		try {
			jedis = connectionFactory.getShardInfo().createResource();
			List<Slowlog> logList = jedis.slowlogGet(entries);
			return logList;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取日志条数
	public Long getLogsLen() {
		Jedis jedis = null;
		try {
			jedis = connectionFactory.getShardInfo().createResource();
			long logLen = jedis.slowlogLen();
			return logLen;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 清空日志
	public String logEmpty() {
		Jedis jedis = null;
		try {
			jedis = connectionFactory.getShardInfo().createResource();
			return jedis.slowlogReset();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取占用内存大小
	public Long dbSize() {
		Jedis jedis = null;
		try {
			jedis = connectionFactory.getShardInfo().createResource();
			// 配置redis服务信息
			Client client = jedis.getClient();
			client.dbSize();
			return client.getIntegerReply();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}
}

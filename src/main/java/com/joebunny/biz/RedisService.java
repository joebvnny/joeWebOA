package com.joebunny.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.joebunny.entity.dto.RedisOper;
import com.joebunny.entity.dto.RedisInfo;

import redis.clients.util.Slowlog;

/**
 * 缓存信息服务
 */
@Service
public class RedisService {
	
	@Autowired
	RedisUtils redisUtil;
	
	//获取redis服务器信息
	public List<RedisInfo> getRedisInfo() {
		String info = redisUtil.getRedisInfo();
		List<RedisInfo> ridList = new ArrayList<RedisInfo>();
		String[] strs = info.split("\n");
		RedisInfo rif = null;
		if(strs != null && strs.length > 0) {
			for(int i=0; i<strs.length; i++) {
				rif = new RedisInfo();
				String s = strs[i];
				String[] str = s.split(":");
				if(str != null && str.length > 1) {
					String key = str[0];
					String value = str[1];
					rif.setKey(key);
					rif.setValue(value);
					ridList.add(rif);
				}
			}
		}
		return ridList;
	}
	
	//获取redis日志列表
	public List<RedisOper> getLogs(long entries) {
        List<Slowlog> list = redisUtil.getLogs(entries);
		List<RedisOper> opList = null;
		RedisOper op  = null;
		boolean flag = false;
		if (list != null && list.size() > 0) {
			opList = new LinkedList<RedisOper>();
			for(Slowlog sl : list) {
				String args = JSON.toJSONString(sl.getArgs());
				if(args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
					continue;
				}	
				op = new RedisOper();
				flag = true;
				op.setId(sl.getId());
				op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
				op.setUsedTime(sl.getExecutionTime()/1000.0 + "ms");
				op.setArgs(args);
				opList.add(op);
			}
		} 
		if(flag) 
			return opList;
		else 
			return null;
	}
	
	//获取日志总数
	public Long getLogLen() {
		return redisUtil.getLogsLen();
	}
	
	//获取当前数据库中key的数量
	public Map<String,Object> getKeysSize() {
		long dbSize = redisUtil.dbSize();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("create_time", new Date().getTime());
		map.put("dbSize", dbSize);
		return map;
	}
	
	//获取当前redis使用内存大小情况
	public Map<String,Object> getMemeryInfo() {
		String[] strs = redisUtil.getRedisInfo().split("\n");
		Map<String, Object> map = null;
		for(int i=0; i<strs.length; i++) {
			String s = strs[i];
			String[] detail = s.split(":");
			if(detail[0].equals("used_memory")) {
				map = new HashMap<String, Object>();
				map.put("used_memory",detail[1].substring(0, detail[1].length()-1));
				map.put("create_time", new Date().getTime());
				break;
			}
		}
		return map;
	}
	
    //清空日志
    public String logEmpty() {
        return redisUtil.logEmpty();
    }
    
	private String getDateStr(long timeStmp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date(timeStmp));
	}
}
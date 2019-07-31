package com.joebunny.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.joebunny.biz.RedisService;
import com.joebunny.entity.dto.RedisInfo;
import com.joebunny.entity.dto.RedisOper;

/**
 * 缓存信息服务控制器
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    // 跳转到Redis监控页面
    @RequestMapping("/redisMonitor.do")
    public String redisMonitor(Model model) {
        // 获取redis的info
        List<RedisInfo> ridList = redisService.getRedisInfo();
        // 获取redis的日志记录
        List<RedisOper> logList = redisService.getLogs(1000);
        // 获取日志总数
        long logLen = redisService.getLogLen();
        model.addAttribute("infoList", ridList);
        model.addAttribute("logList", logList);
        model.addAttribute("logLen", logLen);
        return "/admin/redisMonitor.jsp";
    }

    // 获取当前内存使用情况
    @RequestMapping("/getMemeryInfo.do")
    @ResponseBody
    public String getMemeryInfo() {
        return JSON.toJSONString(redisService.getMemeryInfo());
    }
    
    // 获取当前缓存中key的数量
    @RequestMapping("/getKeysSize.do")
    @ResponseBody
    public String getKeysSize() {
        return JSON.toJSONString(redisService.getKeysSize());
    }

    // 清空Redis日志
    @RequestMapping(value = "/logEmpty.do")
    @ResponseBody
    public String logEmpty() {
        return redisService.logEmpty();
    }

}
package com.joebunny.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * ID生成器
 */
public class IdGenerator {
	
    /**
     * 生成平台统一支付订单号
     */
    public static final String genPayOrderId() {
        String machineId = "JB"; //分布式环境前缀
        String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuidStr = "X" + genRandomStr(13);
//      int hashCodeV = UUID.randomUUID().toString().hashCode();
//      if(hashCodeV < 0) {
//          hashCodeV = - hashCodeV;
//      }
//      String uuidStr = String.format("%016d", hashCodeV);
        return (machineId + timeStr + uuidStr);
    }
	
    /**
     * 生成指定长度随机串
     * @param length    字串长度
     * @return RandomString
     */
    public static final String genRandomStr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String result = "";
        if(length <= 0) {
            return result;
        }
        for(int i=0; i<length; i++) {
            Random rd = new Random();
            result += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return result;
    }
    
    /**
     * 生成32位UUID串
     * @return UUIDString
     */
    public static final String genUUIDStr() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
    
}
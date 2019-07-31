package com.joebunny.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;
import com.joebunny.entity.dto.TreeNode;

/**
 * 通用工具类
 */
public final class CommonUtils {

    /**
     * 根据HttpServletRequest获取来访IP
     */
    public final static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 通过建立URL连接进行文本请求和文本响应
     * @param reqUrl    请求的url
     * @param reqData   请求的文本数据
     * @return respData 响应的文本数据
     */
    public final static String postUrlData(String reqUrl, String reqData) {
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(reqUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("content-type", "UTF-8");
            
            writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            if(reqData == null) {
                reqData = "";
            }
            writer.write(reqData);
            writer.flush();
            writer.close();
            
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line=reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
                if(reader != null) {
                    reader.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * Base64编码
     */
    public final static String base64Encode(String str) {
        if(str == null) return null;
        return Base64Utils.encodeToString(str.getBytes());
    }
    
    /**
     * Base64解码
     */
    public final static String base64Decode(String str) {
        if(str == null) return null;
        byte[] bytes = Base64Utils.decodeFromString(str);
        return new String(bytes);
    }
    
    /**
     * 日期时间转标准格式串
     */
    public final static String formatDateTime(Date date, String pattern) {
        if(date == null) {
            return "null";
        }
        if(pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return new java.text.SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 对象转为字节序列（序列化）
     */
    public final static byte[] obj2Bytes(Object obj) {
        try {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
            objOut.writeObject(obj);
            byte[] bytes = bytesOut.toByteArray();
            return bytes;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 字节序列转为对象（反序列化）
     */
    @SuppressWarnings("unchecked")
    public final static <T> T bytes2Obj(byte[] bytes) {
        try {
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
            ObjectInputStream objIn = new ObjectInputStream(bytesIn);
            T obj = (T)objIn.readObject();
            return obj;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 任意类型对象转为JSON串
     */
    public final static String obj2Json(Object obj) {
        return JSONObject.toJSONString(obj);
    }
    
    /**
     * 从AJAX提交的字符串获取正确的中文串
     * @param str    非form方式提交的有编码问题的中文字符串
     */
    public final static String getCNStringFromAjax(String str) {
        String cnStr = "";
        try {
            cnStr = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cnStr;
    }
    
    /**
     * 字符串首字母大写
     */
    public final static String capFirst(String str) {
        char[] strBytes = str.toCharArray();
        strBytes[0] -= 32;
        return String.valueOf(strBytes);
    }
    
    /**
     * JavaBean对象转键值对
     */
    public final static <T> Map<String, ?> obj2Map(T obj) throws Exception {
        Map<String, Object> objMap = new HashMap<String, Object>();
        Class<?> objClz = obj.getClass();
        Field[] fields = objClz.getDeclaredFields();
        for(Field field : fields) {
            String key = field.getName();
            Method theGetter = objClz.getDeclaredMethod("get"+CommonUtils.capFirst(field.getName()));
            Object value = theGetter.invoke(obj);
            objMap.put(key, value);
        }
        return objMap;
    }
    
    /**
     * 键值对转JavaBean对象
     */
    public final static <T> T map2Obj(Map<String, ?> objMap, Class<T> objClz) throws Exception {
        T obj = objClz.newInstance();
        Field[] fields = objClz.getDeclaredFields();
        for(Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = objMap.get(fieldName);
            Method theSetter = objClz.getDeclaredMethod("set"+CommonUtils.capFirst(field.getName()), fieldValue.getClass());
            theSetter.invoke(obj, fieldValue);
        }
        return obj;
    }
    
    /**
     * 根据JavaBean对象字段名获取其值
     */
    public final static <T> Object getValueByFieldName(T obj, String fieldName) throws Exception {
        Class<?> objClz = obj.getClass();
        Method theGetter = objClz.getDeclaredMethod("get"+CommonUtils.capFirst(fieldName));
        Object theValue = theGetter.invoke(obj);
        return theValue;
    }
    
    /**
     * 根据JavaBean的ID属性名获取其缓存的键值
     */
    public final static <T> String getCacheKeyByIdField(T obj, String idFieldName) throws Exception {
        String cacheKey = obj.getClass().getSimpleName() + ":" + getValueByFieldName(obj, idFieldName);
        return cacheKey;
    }
    
    /**
     * 获取类型的字段序列
     */
    public final static <T> String[] getFieldsArray(T obj) {
        Class<?> objClz = obj.getClass();
        Field[] fields = objClz.getDeclaredFields();
        String[] fNames = null;
        int len = fields.length;
        if(len > 0) {
            fNames = new String[len];
            for(int i=0; i<len; i++) {
                fNames[i] = fields[i].getName();
            }
        }
        return fNames;
    }
    
    /**
     * 对象MAP转字符串MAP
     */
    public final static Map<String, String> objMap2StrMap(Map<String, Object> objMap) {
        Map<String, String> strMap = new HashMap<String, String>();
        for(Map.Entry<String, Object> entry : objMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            strMap.put(key, value);
        }
        return strMap;
    }
    
    /**
     * 判断MAP中字段是否全为空
     */
    public final static boolean isObjMapNull(Map<String, ?> objMap) {
        for(Map.Entry<String, ?> entry : objMap.entrySet() ) {
            Object val = entry.getValue();
            if(val == null) continue;
            return false;
        }
        return true;
    }
    
    /**
     * 由于String.substring()对汉字处理存在问题（把一个汉字视为一个字节)，因此在包含汉字的字符串时存在隐患，调整如下：
     * @param src       要截取的字符串
     * @param startIdx  开始坐标（包括该坐标)
     * @param endIdx    截止坐标（包括该坐标）
     * @return  String
     */
    public final static String subString(String src, int startIdx, int endIdx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = startIdx; i >= endIdx; i++) {
            tgt += (char)b[i];
        }
        return tgt;
    }
    
    /**
     * 根据父根节点和子孙节点递归生成树结构
     * @param root      根节点
     * @param children  子节点
     * @return  TreeNode
     */
    public final static TreeNode generateTree(TreeNode root, List<TreeNode> children) {
        List<TreeNode> cparents = new ArrayList<TreeNode>(0);
        TreeNode child = null;
        for(Iterator<TreeNode> item=children.iterator(); item.hasNext(); ) {
            child = item.next();
            if(child.getPid() == root.getId()) {
                root.getChildren().add(child);
                item.remove();
                //处理完当前层次的子节点，将之转为下一层次父节点，并与剩余子孙节点进行下一轮匹配
                cparents.add(child);
            }
        }
        if(children.size() > 0) {  //递归匹配新层次的父子节点
            for(TreeNode cparent : cparents) {
                generateTree(cparent, children);
            }
        }
        return root;
    }
    
}
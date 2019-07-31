package com.joebunny.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 基础JavaBean
 */
@SuppressWarnings("serial")
public class BaseBean implements Cloneable, Serializable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    protected void propertyToString(StringBuilder sb, Field f) throws Exception {
        sb.append(f.getName()).append("=").append(f.get(this));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            boolean isFirst = true;
            sb.append(getClass().getSimpleName()).append("@").append(Integer.toHexString(hashCode())).append(":{");
            Class<?> cls = getClass();
            while(cls != null) {
                Field[] fs = cls.getDeclaredFields();
                for(Field f : fs) {
                    if(!Modifier.isStatic(f.getModifiers())) {
                        if(!isFirst) {
                            sb.append(",");
                        }
                        f.setAccessible(true);
                        this.propertyToString(sb, f);
                        isFirst = false;
                    }
                }
                cls = cls.getSuperclass();
            }
            sb.append("}");
        } catch(Exception e) {
            e.printStackTrace();
            return super.toString();
        }
        return sb.toString();
    }
    
}
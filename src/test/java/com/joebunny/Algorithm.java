package com.joebunny;

import java.util.List;
import java.util.function.Function;

public class Algorithm {
    
    public static Long sumLong(List<Long> list, Function<List<Long>, Long> func) {
        Long sum = func.apply(list);
        return sum;
    }
    
    public static Long sumLong0(List<Long> list) {
        if(list == null || list.size() < 1) {
            return null;
        }
        int size = list.size();
        long sum = (list.get(0) + list.get(size-1)) * size / 2;
        return sum;
    }
    
    public static Long sumLong1(List<Long> list) {
        if(list == null || list.size() < 1) {
            return null;
        }
        int size = list.size();
        long sum = 0;
        for(int i=0; i<size; i++) {
            sum += list.get(i);
        }
        return sum;
    }
    
    //递归求和，数据量大时堆栈溢出
    public static Long sumLong2(List<Long> list) {
        if(list == null || list.size() < 1) {
            return null;
        }
        int size = list.size();
        if(size == 1) {
            return list.get(0);
        } else {
            return list.remove(--size) + sumLong2(list);
        }
    }
    
}
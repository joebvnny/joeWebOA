package com.joebase.findMethods;

public class SeqSearch {

    public static int[] data = {12, 76, 29, 22, 15, 62, 29, 58, 35, 67, 58, 33, 28, 89, 90, 28, 64, 48, 20, 77}; // 输入数据数组

    public static int count = 1; // 查找次数计数变量

    public static void main(String args[]) {

        int key = 58;
        // 调用线性查找
        if(seqSearch(key)) {
            // 输出查找次数
            System.out.println("");
            System.out.println("Search Time = " + count);
        } else {
            // 输出没有找到数据
            System.out.println("");
            System.out.println("No Found!!");
        }
    }

    // ---------------------------------------------------
    // 顺序查找
    // ---------------------------------------------------
    public static boolean seqSearch(int key) {
        int i; // 数据索引计数变量

        for(i = 0; i < 20; i++) {
            // 输出数据
            System.out.print("[" + data[i] + "]");
            // 查找到数据时
            if((int)key == data[i])
                return true; // 传回true
            count++; // 计数器递增
        }
        return false; // 传回false
    }
}
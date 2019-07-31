package com.joebase.findMethods;

public class BinSearch {

    public static int Max = 20;

    public static int[] Data = {12, 16, 19, 22, 25, 32, 39, 48, 55, 57, 58, 63, 68, 69, 70, 78, 84, 88, 90, 97}; // 数据数组

    public static int Counter = 1; // 计数器

    public static void main(String args[]) {
        int key = 58;
        // 调用折半查找
        if(binSearch((int)key)) {
            // 输出查找次数
            System.out.println("");
            System.out.println("Search Time = " + (int)Counter);
        } else {
            // 输出没有找到数据
            System.out.println("");
            System.out.println("No Found!!");
        }
    }

    // ---------------------------------------------------
    // 折半查找法
    // ---------------------------------------------------
    public static boolean binSearch(int key) {
        int Left; // 左边界变量
        int Right; // 右边界变量
        int Middle; // 中位数变量

        Left = 0;
        Right = Max - 1;

        while(Left <= Right) {
            Middle = (Left + Right) / 2;
            if(key < Data[Middle]) // 欲查找值较小
                Right = Middle - 1; // 查找前半段
            // 欲查找值较大
            else if(key > Data[Middle])
                Left = Middle + 1; // 查找后半段
            // 查找到数据
            else {
                System.out.println("Data[" + Middle + "] = " + Data[Middle]);
                return true;
            }
            Counter++;
        }
        return false;
    }
}
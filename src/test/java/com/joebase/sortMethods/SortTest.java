package com.joebase.sortMethods;

import java.util.Arrays;

public class SortTest {

    public static void main(String[] args) {
        Integer[] data = {8, 9, 7, 5, 4, 0, 2, 3, 6, 1};
        System.out.println(Arrays.toString(data));
        quickSort(data);
        System.err.println(Arrays.toString(data));
    }

    private static void swap(Object[] array, int p, int q) {
        if(p == q) return;
        Object temp = array[p];
        array[p] = array[q];
        array[q] = temp;
    }
    
    public static void insertSort(Integer[] array) {
        int len = array.length;
        int temp = Integer.MIN_VALUE;
        int i, j;
        for(i=1; i<len; i++) {
            temp = array[i];
            for(j=i; j>0 && temp<array[j-1]; j--) {
                array[j] = array[j-1];
            }
            array[j] = temp;
            System.err.println(Arrays.toString(array));
        }
    }
    
    public static void bubbleSort(Integer[] array) {
        int len = array.length;
        int i, j;
        boolean flag = true;
        for(i=1; i<len && flag; i++) {
            flag = false;
            for(j=0; j<len-i; j++) {
                if(array[j] > array[j+1]) {
                    swap(array, j, j+1);
                    flag = true;
                }
            }
            System.err.println(Arrays.toString(array));
        }
    }
    
    public static void quickSort(Integer[] array) {
        qSort(array, 0, array.length-1);
    }
    
    private static void qSort(Integer[] array, int lo, int hi) {
        if(lo >= hi) return;
        Integer temp = array[lo];
        int leftIdx = lo;
        int rightIdx = hi;
        int i = lo+1;
        while(i <= rightIdx) {
            if(array[i] == temp) {
                i++;
            } else if(array[i]<temp) {
                swap(array, leftIdx++, i++);
            } else {
                swap(array, rightIdx--, i);
            }
            System.err.println(Arrays.toString(array));
        }
        qSort(array, lo, leftIdx-1);
        qSort(array, rightIdx+1, hi);
    }

    public static void selectSort(Integer[] array) {
        int len = array.length;
        int i, j, flag;
        for(i=0; i<len; i++) {
            flag = i;
            for(j=i+1; j<len; j++) {
                if(array[j] < array[flag]) {
                    flag = j;
                }
            }
            if(flag != i) {
                swap(array, flag, i);
            }
            System.err.println(Arrays.toString(array));
        }
    }
    
}
package com.joebase.findMethods;

public class SearchUtil {

    public static void main(String[] args) {
        Integer[] data = {12, 16, 19, 22, 25, 32, 39, 48, 55, 57, 58, 63, 68, 69, 70, 78, 84, 88, 90, 97};
        Integer key = 99;
        System.out.println("Found Index = " + binSearch(key, data));
//        int[] data = {-2, 11, -4, 13, -5, 2, -5, -3, 12, -9};
//        System.out.println(maxSub(data));
    }
    
    public static int maxSub(int[] array) {
        int max = 0;
        int len = array.length;
        int sum = 0;
        for(int i=0; i<len; i++) {
            sum += array[i];
            if(sum > max) {
                max = sum;
            } else if(sum < 0) {
                sum = 0;
            }
        }
        return max;
    }
    
    public static <T extends Comparable<T>> int seqSearch(T key, T[] array) {
        int scale = array.length;
        int index = -1;
        int count = 0;
        for(int i=0; i<scale; i++) {
            count++;
            if(array[i].compareTo(key) == 0) {
                index = i+1;
                return index;
            }
        }
        System.out.println("Search Time : " + count);
        return index;
    }
    
    public static <T extends Comparable<T>> int binSearch(T key, T[] array) {
        
        int left = 0, right = array.length-1, mid = -1;
        int index = -1;
        int count = 0;
        while(left <= right) {
            count++;
            mid = (left + right) / 2;
            if(array[mid].compareTo(key) == 0) {
                index = mid+1;
                return index;
            } else if(array[mid].compareTo(key) > 0) {
                right = mid - 1;
            } else if(array[mid].compareTo(key) < 0) {
                left = mid + 1;
            }
        }
        System.out.println("Search Time : " + count);
        return index;
    }

}
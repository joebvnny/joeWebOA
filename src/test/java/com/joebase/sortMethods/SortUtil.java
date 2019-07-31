package com.joebase.sortMethods;

import java.util.Arrays;

public class SortUtil {

    public static void main(String[] args) {
        Integer[] data = {8, 9, 7, 5, 4, 2, 3, 6, 1};
        System.err.println(Arrays.toString(data));
        int count = quickSort(data, true);
        System.err.println("sorted time = " + count);
        System.err.println(Arrays.toString(data));
    }

    private static <T> void swap(T[] array, int p, int q) {
        if(p == q) return;
        T temp = array[p];
        array[p] = array[q];
        array[q] = temp;
    }
    
    public static <T extends Comparable<T>> int bubbleSort(T[] array, boolean ascend) {
        int scale = array.length;
        int compare = 0;
        int count = 0;
        boolean swapFlag = true;
//        for(int i=0; i<scale; i++) {
//            for(int j=i+1; j<scale; j++) {
//                count++;
//                compare = array[i].compareTo(array[j]);
//                if((compare>0) == ascend) {
//                    swap(array, i, j);
//                }
//            }
//        }
        for(int i=0; i<scale && swapFlag; i++) {
            swapFlag = false; //无交换表明已有序
            for(int j=scale-1; j>i; j--) {
                count++;
                compare = array[j-1].compareTo(array[j]);
                if(compare>0 == ascend) {
                    swap(array, j-1, j);
                    swapFlag = true;
                }
                System.out.println(Arrays.toString(array));
            }
        }
        return count;
    }
    
    public static <T extends Comparable<T>> int selectSort(T[] array, boolean ascend) {
        int scale = array.length;
        int compare = 0;
        int count = 0;
        int flag = -1;
        for(int i=0; i<scale; i++) {
            flag = i;
            for(int j=i+1; j<scale; j++) {
                count++;
                compare = array[flag].compareTo(array[j]);
                if(compare>0 == ascend) {
                    flag = j;
                }
            }
            if(i != flag) {
                swap(array, i, flag);
            }
            System.out.println(Arrays.toString(array));
        }
        return count;
    }
    
    public static <T extends Comparable<T>> int insertSort(T[] array, boolean ascend) {
        int scale = array.length;
        int compare = 0;
        int count = 0;
        for(int i=1; i<scale; i++) {
            T element = array[i];
            int j=i;
            for(; j>0; j--) {
                count++;
                compare = array[j-1].compareTo(element);
                if((compare<0 || compare==0) == ascend) {
                    break;
                }
                array[j] = array[j-1];
            }
            array[j] = element;
            System.out.println(Arrays.toString(array));
        }
        return count;
    }
    
    public static <T extends Comparable<T>> int shellSort(T[] array, boolean ascend) {
        int scale = array.length;
        int compare = 0;
        int count = 0;
        int gap = 1;

        while(gap < scale/3) {
            gap = gap * 3 + 1;
        }

        while(gap >= 1) {
            for(int i=gap; i<scale; i++) {
                T next = array[i];
                int j = i;
                while(j >= gap) {
                    count++;
                    compare = array[j-gap].compareTo(next);
                    if((compare<0 || compare==0) == ascend) {
                        break;
                    }
                    array[j] = array[j-gap];
                    j -= gap;
                }
                if(j != i) {
                    array[j] = next;
                }
            }
            gap /= 3;
        }
        return count;
    }
    
    public static <T extends Comparable<T>> int quickSort(T[] array, boolean ascend) {
        return qSort(array, 0, array.length-1, ascend);
    }
    
    private static <T extends Comparable<T>> int qSort(T[] array, int lo, int hi, boolean ascend) {
        int count = 0;
        int compare = 0;
        if(lo >= hi) return count;
        T element = array[lo];
        int leftIdx = lo;
        int rightIdx = hi;
        int i = lo+1;
        while(i <= rightIdx) {
            count++;
            compare = array[i].compareTo(element);
            if(compare == 0) {
                i++;
            } else if(compare<0 == ascend) {
                swap(array, leftIdx++, i++);
            } else {
                swap(array, rightIdx--, i);
            }
            System.out.println(Arrays.toString(array));
        }
        qSort(array, lo, leftIdx-1, ascend);
        qSort(array, rightIdx+1, hi, ascend);
        return count;
    }
    
}
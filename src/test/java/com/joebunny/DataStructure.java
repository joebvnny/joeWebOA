package com.joebunny;

import java.util.Arrays;

public class DataStructure {
    
    private static final int[] data = new int[] {54, 32, 87, 22, 96, 60, 22, 18, 41, 79};
    
    public static void main(String[] args) {
        System.out.println(Arrays.toString(data));
        mergeSort(data, 0, 9);
        System.out.println(Arrays.toString(data));
    }
    
    public static void bubbleSort(int[] arr) {
        int n = arr.length - 1;
        int temp;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n-i; j++) {
                if(arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    
    public static void quickSort(int[] arr, int start, int end) {
        if(start < end) {
            int tag = arr[start];
            int low = start;
            int high = end;
            
            while(low < high) {
                while(low < high && arr[high] >= tag) {
                    high --;
                }
                arr[low] = arr[high];
                
                while(low < high && arr[low] <= tag) {
                    low ++;
                }
                arr[high] = arr[low];
            }
            arr[low] = tag;
            
            quickSort(arr, start, low-1);
            quickSort(arr, low+1, end);
        }
    }
    
    public static void insertSort(int[] arr) {
        int n = arr.length;
        int temp;
        for(int i=1; i<n; i++) {
            if(arr[i] < arr[i-1]) {
                temp = arr[i];
                int j;
                for(j=i-1; j>=0 && temp < arr[j]; j--) {
                    arr[j+1] = arr[j];
                }
                arr[j+1] = temp;
            }
        }
    }
    
    public static void shellSort(int[] arr) {
        int n = arr.length;
        int temp;
        for(int d=n/2; d>0; d/=2) {
            for(int i=d; i<n; i++) {
                for(int j=i-d; j>=0; j-=d) {
                    if(arr[j] > arr[j+d]) {
                        temp = arr[j];
                        arr[j] = arr[j+d];
                        arr[j+d] = temp;
                    }
                }
            }
        }
    }
    
    public static void selectSort(int[] arr) {
        int n = arr.length;
        int minIdx;
        int temp;
        for(int i=0; i<n; i++) {
            minIdx = i;
            for(int j=i+1; j<n; j++) {
                if(arr[minIdx] > arr[j]) {
                    minIdx = j;
                }
            }
            if(i != minIdx) {
                temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
            }
        }
    }
    
    public static void mergeSort(int[] arr, int low, int high) {
        int middle = (high + low) / 2;
        if(low < high) {
            mergeSort(arr, low, middle);
            mergeSort(arr, middle+1, high);
            merge(arr, low, middle, high);
        }
    }
    
    private static void merge(int[] arr, int low, int middle, int high) {
        int[] temp = new int[high-low+1];
        int i = low;
        int j = middle + 1;
        int idx = 0;
        while(i<=middle && j<=high) {
            if(arr[i] <= arr[j]) {
                temp[idx++] = arr[i++];
            } else {
                temp[idx++] = arr[j++];
            }
        }
        while(j<=high) {
            temp[idx++] = arr[j++];
        }
        while(i<=middle) {
            temp[idx++] = arr[i++];
        }
        
        for(int k=0; k<temp.length; k++) {
            arr[k+low] = temp[k];
        }
    }
    
}
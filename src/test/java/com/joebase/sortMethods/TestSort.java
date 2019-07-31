package com.joebase.sortMethods;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class TestSort {

    // define the array's size
    private final int PROBLEM_SCALE = 10;

    private boolean checkSorted = true;

    private boolean showDebugInfo = true;

    @Test
    public void selectionSort() {
        sort(SortType.SELECTION);
    }

    @Test
    public void insertionSort() {
        sort(SortType.INSERTION);
    }

    @Test
    public void bubbleSort() {
        sort(SortType.BUBBLE);
    }

    @Test
    public void shellSort() {
        sort(SortType.SHELL);
    }

    @Test
    public void mergeSort() {
        sort(SortType.MERGE);
    }

    @Test
    public void quickSort() {
        sort(SortType.QUICK);
    }

    @Test
    public void heapSort() {
        sort(SortType.HEAP);
    }

    private void sort(SortType sortType) {
        Integer[] array = generateArray(PROBLEM_SCALE);
        if(showDebugInfo) {
            System.out.println("Before: " + Arrays.toString(array));
        }
        sortType.sort(array);
        if(showDebugInfo) {
            System.out.println("After " + sortType.name() + " sort: " + Arrays.toString(array));
        }
        if(checkSorted) {
            Assert.assertTrue(isSorted(array));
        }
    }

    private Integer[] generateArray(int length) {
        Random rand = new Random();
        Integer[] array = new Integer[length];

        for(int i = 0; i < length; i++) {
            array[i] = rand.nextInt(length * 4);
        }
        return array;
    }

    private <T extends Comparable<T>> boolean isSorted(T[] array) {
        if(array == null || array.length <= 2) {
            return true;
        }

        // record the result of last comparison
        Boolean lastCompare = null;

        for(int i = 1; i < array.length; i++) {
            int compareResult = array[i - 1].compareTo(array[i]);
            if(lastCompare == null || compareResult == 0) {
                if(compareResult != 0) {
                    lastCompare = compareResult > 0;
                }
                continue;
            }

            if(compareResult > 0 != lastCompare) {
                return false;
            }
        }

        return true;
    }
}

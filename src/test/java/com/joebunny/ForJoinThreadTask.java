package com.joebunny;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForJoinThreadTask {

    public static void main(String[] args) throws Exception {
        
        int[] arr = new int[100000000];
        long total = 0;
        
        long t1 = System.currentTimeMillis();
        for(int i=0; i<arr.length; i++) {
            total += (arr[i] = i+1);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("初始化数组总和：" + total + ", 用时；" + (t2-t1));
        
        SumTask task = new SumTask(arr, 0, arr.length);
        ForkJoinPool threadPool = ForkJoinPool.commonPool();
        long t3 = System.currentTimeMillis();
        Future<Long> result = threadPool.submit(task);
//        long result = threadPool.invoke(task);
        long t4 = System.currentTimeMillis();
        System.out.println("多线程执行结果：" + result.get() + ", 用时；" + (t4-t3));
        
    }
}

@SuppressWarnings("serial")
class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 20000000; //子任务处理规模
    
    private int arr[];
    private int start;
    private int end;
    
    public SumTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Long compute() {
        if(end - start <= THRESHOLD) {  //若数据批量在预定处理规模内，则正常业务处理
            long sum = 0;
            for(int i=start; i<end; i++) {
                sum += arr[i];
            }
            System.out.println("[" + Thread.currentThread().getName() + "]compute() : sum=" + sum);
            return sum;
        } else {  //若数据批量超过处理规模，则fork拆分任务（一分为二）
            int middle = (start + end) / 2;
            SumTask leftTask = new SumTask(arr, start, middle);
            SumTask rightTask = new SumTask(arr, middle, end);
            // 并行执行拆分好的子任务
            leftTask.fork();
            rightTask.fork();
            // 获取子任务结果，合并总结果
            long result1 = leftTask.join();
//            System.out.println("[" + Thread.currentThread().getName() + "]left.fork().join() : result=" + result1);
            long result2 = rightTask.join();
//            System.out.println("[" + Thread.currentThread().getName() + "]right.fork().join() : result=" + result2);
            return result1 + result2;
        }
    }
}
package com.joebase.thread;

public class PC_Client {

    public static void main(String[] args) {
        
        ResourcePool pool = new ResourcePool();
        ProduceTask produceTask = new ProduceTask(pool);
        ConsumeTask consumeTask = new ConsumeTask(pool);
        
        Thread p1 = new Thread(produceTask, "p1");
        Thread p2 = new Thread(produceTask, "p2");
        Thread p3 = new Thread(produceTask, "p3");
        
        Thread c1 = new Thread(consumeTask, "c1");
        Thread c2 = new Thread(consumeTask, "c2");
        
        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        p3.interrupt();
        
    }

}

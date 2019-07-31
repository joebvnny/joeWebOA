package com.joebase.thread;

import java.util.Random;

public class ConsumeTask implements Runnable {
    
    private ResourcePool pool;

    public ConsumeTask(ResourcePool pool) {
        this.pool = pool;
    }
    
    public Resource consume() {
        Resource resource = null;
        try {
            resource = this.pool.getResource();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(new Random().nextInt(5000));
                this.consume();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

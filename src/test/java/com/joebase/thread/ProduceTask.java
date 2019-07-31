package com.joebase.thread;

import java.util.Random;

public class ProduceTask implements Runnable {
    
    private ResourcePool pool;

    public ProduceTask(ResourcePool pool) {
        this.pool = pool;
    }
    
    public Resource produce(Resource resource) {
        try {
            this.pool.putResource(resource);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(new Random().nextInt(2000));
                this.produce(new Resource(((Long)(System.currentTimeMillis())).toString()));
            } catch(InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
                System.out.println(Thread.currentThread().getName() + " interruptedFlag=" + Thread.currentThread().isInterrupted());
                break;
            }
        }
    }
}

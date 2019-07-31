package com.joebase.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ResourcePool {
    
    private static final int POOL_SIZE = 4;
    private BlockingQueue<Resource> pool = new ArrayBlockingQueue<Resource>(POOL_SIZE);
    
    public ResourcePool() {
    }
    
    public ResourcePool(BlockingQueue<Resource> pool) {
        this.pool = pool;
    }

    public synchronized void putResource(Resource resource) throws Exception {
        while(this.pool.size() >= POOL_SIZE) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + " putting " + resource);
        this.pool.put(resource);
        this.notifyAll();
    }
    
    public synchronized Resource getResource() throws Exception {
        while(this.pool.size() <= 0) {
            this.wait();
        }
        Resource resource = this.pool.take();
        System.out.println(Thread.currentThread().getName() + " getting " + resource);
        this.notifyAll();
        return resource;
    }
    
}

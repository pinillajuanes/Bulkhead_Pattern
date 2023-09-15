package com.pattern.bulkhead.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class QueueService {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);

    public void enqueue(String message) throws InterruptedException {
        queue.put(message);
    }

    public String dequeue() throws InterruptedException {
        return queue.take();
    }
}

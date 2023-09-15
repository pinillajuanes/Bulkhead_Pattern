package com.pattern.bulkhead.controller;

import com.pattern.bulkhead.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController{

    @Autowired
    private QueueService queueService;

    @PostMapping("/enqueue")
    public String enqueue(@RequestBody String message) throws InterruptedException {
        queueService.enqueue(message);
        return "Message added to the queue: " + message;
    }

    @GetMapping("/dequeue")
    public String dequeue() throws InterruptedException {
        String message = queueService.dequeue();
        if (message.equals("No messages in the queue")) {
            return message;
        } else {
            return "Message dequeued from the queue: " + message;
        }
    }
}
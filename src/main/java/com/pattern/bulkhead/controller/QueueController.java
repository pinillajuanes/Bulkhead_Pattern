package com.pattern.bulkhead.controller;

import com.pattern.bulkhead.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/enqueue")
    public String enqueue() throws InterruptedException {
        queueService.enqueue("Message added to the queue");
        return "Message added to the queue";
    }

    @GetMapping("/dequeue")
    public String dequeue() throws InterruptedException {
        return queueService.dequeue();
    }
}

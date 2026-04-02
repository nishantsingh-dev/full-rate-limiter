
package com.ratelimiter.analytics;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "events", groupId = "group")
    public void consume(String msg){
        System.out.println("Event: " + msg);
    }
}

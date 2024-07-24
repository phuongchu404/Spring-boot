package com.activemq.controller;

import com.activemq.dto.Store;
import com.activemq.dto.StoreMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StoreMessageController {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private StoreMessageProducer storeMessageProducer;

    @Value("${activemq.destination}")
    private String destination;

    @PostMapping("/publish")
    public String publishMessage(@RequestBody Store store){
        storeMessageProducer.sendTo(destination, store);
        return "success";
    }
}

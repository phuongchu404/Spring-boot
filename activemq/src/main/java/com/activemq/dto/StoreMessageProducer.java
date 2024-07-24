package com.activemq.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StoreMessageProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendTo(String destination, Store store){
        jmsTemplate.convertAndSend(destination, store);
        log.info("Producer > Message sent");
    }
}

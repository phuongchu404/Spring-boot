package com.activemq.api;

import com.activemq.dto.Store;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StoreMessageConsumer {
    @JmsListener(destination = "${activemq.destination}", containerFactory = "jmsFactory")
    public void processToDo(Store store){
        log.info("Consumer> " + store);
    }

    @JmsListener(destination = "${activemq.destination}")
    public void receiveMessage(Message jsonMessage) throws JMSException{
        System.out.println("message: " + jsonMessage);
    }
}

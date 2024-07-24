package com.mqtt.emqx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

@SpringBootTest(classes = EmqxApplication.class)
public class MqttCustomerClientTest {
    @Autowired
    private MqttCustomerClient mqttCustomerClient;

    @Test
    void publish(){
        for (int i = 0; i < 20; i++) {
            mqttCustomerClient.pushlish("device", "test ...." + i);
            try {
                Thread.sleep(2000);

            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    void subscribe(){
        mqttCustomerClient.subscribe("device");
    }
}

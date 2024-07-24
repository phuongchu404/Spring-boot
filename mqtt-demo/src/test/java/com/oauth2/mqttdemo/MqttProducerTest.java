package com.oauth2.mqttdemo;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class MqttProducerTest {
    @Autowired
    MqttUtils mqttUtil;

    @Test
    void pushMessateTest() throws Exception {
        for (int i = 0; i < 20; i++) {
            String topic = "test";
            mqttUtil.publish(topic, "Time：" + DateUtil.now());
            Thread.sleep(3000);
        }
        new CountDownLatch(1).await();
    }
}

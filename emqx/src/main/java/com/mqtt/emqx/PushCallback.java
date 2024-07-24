package com.mqtt.emqx;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushCallback implements MqttCallback {
    private static MqttClient client;
    @Override
    public void connectionLost(Throwable throwable) {
        if(client == null && !client.isConnected()) {
            log.info("Connection lost");
        }
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        log.info("topic: {}", s);
        log.info("Qos: {}", mqttMessage.getQos());
        log.info("payload: {}", mqttMessage.getPayload());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("delivery complete: {}", iMqttDeliveryToken.isComplete());
    }
}

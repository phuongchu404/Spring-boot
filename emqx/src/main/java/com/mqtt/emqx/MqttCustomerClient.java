package com.mqtt.emqx;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttCustomerClient {
    @Autowired
    private PushCallback pushCallback;

    private static MqttClient client;

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client){
        MqttCustomerClient.client = client;
    }

    public void connect(String host, String clientId, String username, String password, int timeout, int keepAlive){
        MqttClient client;
        try {
            client = new MqttClient(host, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepAlive);
            MqttCustomerClient.setClient(client);
            try {
                client.setCallback(pushCallback);
                client.connect(options);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public void pushlish(String topic, String pushMessage){
        pushlish(0, true,topic, pushMessage);
    }
    public void pushlish(int qos, boolean retained, String topic, String pushMessage){
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mqttTopic = MqttCustomerClient.getClient().getTopic(topic);
        if(null == mqttTopic){
            log.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();
        }catch (MqttPersistenceException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void subscribe(String topic){
        log.error("loi topic: {}", topic);
        subscribe(topic, 0);
    }
    public void subscribe(String topic, int qos){
        try {
            MqttCustomerClient.getClient().subscribe(topic, qos);
        }catch (MqttException e){
            e.printStackTrace();
        }
    }
}

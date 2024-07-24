package com.oauth2.mqttdemo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
@Getter
@Setter
public class MqttConnection {
    private MqttClient mqttClient;

    private GenericObjectPool<MqttConnection> belongedPool;

    public MqttConnection(MqttClient mqttClient){
        this.mqttClient = mqttClient;
    }

    public void publish(String topic, String message ) throws Exception {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttClient.publish(topic, mqttMessage);
        System.out.println(mqttClient + ": " + mqttMessage);
    }

    public void destroy(){
        try{
            if(this.mqttClient.isConnected()) {
                this.mqttClient.disconnect();
            }
            this.mqttClient.close();
        } catch (MqttException e) {
            log.error("MqttConnection destroy ERROR ; errorMsg={}", e.getMessage(), e, e);
        }
    }

    public void close(){
        if(belongedPool!=null){
            this.belongedPool.returnObject(this);
        }
    }
}

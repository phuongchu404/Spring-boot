package com.oauth2.mqttdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttUtils {
    @Autowired
    private MqttClientManager mqttClientManager;

    public void publish(String clientId, String message){
        log.info("publish INFO ; clientId={}, message={}", clientId, message);
        MqttConnection connection = null;
        try {
            connection = mqttClientManager.getConnection();
            log.info("publish INFO ; clientId={},targetUrl={}", clientId, connection.getMqttClient().getServerURI());
            connection.publish(clientId, message);
        } catch (Exception e) {
            log.error("publish ERROR ; clientId={},message={}", clientId, message, e, e);
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }
}

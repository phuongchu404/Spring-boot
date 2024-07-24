package com.mqtt.emqx;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfiguration {
    @Autowired
    private MqttCustomerClient mqttCustomerClient;

    private String host;
    private String clientId;
    private String username;
    private String password;
    private String topic;
    private int timeout;
    private int  keepalive;

    @Bean
    public MqttCustomerClient getMqttCustomerClient() {
        mqttCustomerClient.connect(host, clientId, username,password, timeout, keepalive);
        mqttCustomerClient.subscribe("device");
        return mqttCustomerClient;
    }
}

package com.oauth2.mqttdemo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "mqtt")
@Configuration
@Getter
@Setter
public class MqttConfig {
    private String host;
    private String clientId;
    private String username="test";
    private String password="123456";
    private PoolConfig poolConfig;
}

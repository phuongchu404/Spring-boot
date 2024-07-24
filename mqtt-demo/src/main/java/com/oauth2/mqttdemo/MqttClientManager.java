package com.oauth2.mqttdemo;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttClientManager implements InitializingBean {
    private final MqttConfig mqttConfig;

    private MqttConnectionPool<MqttConnection> mqttPool;

    public MqttClientManager(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            this.initPoolConfig(poolConfig);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setUserName(this.mqttConfig.getUsername());
            if(StrUtil.isNotEmpty(mqttConfig.getPassword())){
                connectOptions.setPassword(this.mqttConfig.getPassword().toCharArray());
            }
            MqttConnectionFactory connectionFactory = new MqttConnectionFactory(mqttConfig.getHost(),connectOptions);
            mqttPool = new MqttConnectionPool<>(connectionFactory, poolConfig);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

    private void initPoolConfig(GenericObjectPoolConfig poolConfig) {
        PoolConfig mqttPoolConfig = this.mqttConfig.getPoolConfig();
        if (mqttPoolConfig.isCustomSet()) {
            poolConfig.setMinIdle(mqttPoolConfig.getMinIdle());
            poolConfig.setMaxIdle(mqttPoolConfig.getMaxIdle());
            poolConfig.setMaxIdle(mqttPoolConfig.getMaxTotal());
        }
    }
    public MqttConnection getConnection() throws Exception {
        return this.mqttPool.borrowObject();
    }
}

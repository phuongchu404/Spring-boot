package com.oauth2.mqttdemo;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class MqttConnectionPool<T> extends GenericObjectPool<MqttConnection> {
    public MqttConnectionPool(MqttConnectionFactory factory, GenericObjectPoolConfig config){
        super(factory, config);
    }

    @Override
    public MqttConnection borrowObject() throws Exception {
        MqttConnection connection = super.borrowObject();
        if(connection.getBelongedPool() == null){
            connection.setBelongedPool(this);
        }
        return connection;
    }
    @Override
    public void returnObject(MqttConnection connection){
        if(connection!=null){
            super.returnObject(connection);
        }
    }
}

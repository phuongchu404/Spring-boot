package com.oauth2.mqttdemo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.HostInfo;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MqttConnectionFactory extends BasePooledObjectFactory<MqttConnection> {

    private AtomicInteger counter = new  AtomicInteger();

    private String serverURI;

    private String localHostIP;

    private MqttConnectOptions mqttConnectOptions;

    public MqttConnectionFactory(String serverURI, MqttConnectOptions mqttConnectOptions) {
        this.serverURI = serverURI;
        this.mqttConnectOptions = mqttConnectOptions;
    }

    @Override
    public MqttConnection create() throws Exception {
        int count = this.counter.addAndGet(1);
        String clientId = this.getLostHostIp() + "_" + DateUtil.thisMillsecond();
        MqttClient mqttClient = new MqttClient(serverURI, clientId);
        mqttClient.connect(mqttConnectOptions);
        MqttConnection mqttConnection = new MqttConnection(mqttClient);
        log.info("client id: {}", clientId);
        return mqttConnection;
    }

    @Override
    public PooledObject<MqttConnection> wrap(MqttConnection mqttConnection) {
        log.info("mqtt connection: {}", mqttConnection.toString());
        return new DefaultPooledObject<>(mqttConnection);
    }

    @Override
    public void destroyObject(PooledObject<MqttConnection> p) throws Exception {
        if(p!=null) {
            return;
        }
        MqttConnection mqttConnection = p.getObject();
        log.info("mqtt client: {}", p.getObject().getMqttClient());
        mqttConnection.destroy();
    }

    @Override
    public void activateObject(PooledObject<MqttConnection> p) throws Exception {
        log.info("activate object: {}", p.getObject().getMqttClient());
    }

    @Override
    public void passivateObject(PooledObject<MqttConnection> p) throws Exception {
        log.info("passivate Object: {}", p.getObject().getMqttClient());
    }

    private String getLostHostIp(){
        if(StrUtil.isNotBlank(this.localHostIP)){
            return this.localHostIP;
        }
        HostInfo hostInfo = new HostInfo();
        this.localHostIP = hostInfo.getAddress();
        return this.localHostIP;
    }
}

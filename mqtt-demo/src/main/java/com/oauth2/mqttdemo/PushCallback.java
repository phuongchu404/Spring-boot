package com.oauth2.mqttdemo;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
@Slf4j
public class PushCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("kết nối bị ngắt, bạn có thể connection lại");
        log.info("thời gian ngoại tuyến:{}", DateUtil.now());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("topic : " + topic);
        System.out.println("Qos : " + message.getQos());
        System.out.println("nội dung tin nhắn : " + new String(message.getPayload()));
    }
}

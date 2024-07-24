package com.oauth2.mqttdemo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoolConfig {
    private boolean customSet;
    private int minIdle;
    private int maxIdle;
    private int maxTotal;

}

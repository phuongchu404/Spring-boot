package com.oauth2.securityoauth.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }
    public void expireValue(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key,timeout,timeUnit);
    }
}

package com.oauth2.securityoauth.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class JsonConvert {
    private static final Gson gson = new Gson();

    public static String convertObjectToJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T convertJsonToObject(String json, Class<T> clazz){
        try {
            return gson.fromJson(json, clazz);
        }catch (JsonSyntaxException e){
            log.error("Json syntax error: {}", e.getMessage());
            return null;
        }
    }
}

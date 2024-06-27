package com.oauth2.securityoauth.security;

import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.utils.JsonConvert;
import com.oauth2.securityoauth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${app.security.jwt-expiration}")
    private Long expiration;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        Optional<String> value = Optional.ofNullable(redisUtils.getValue(token));
        if(!value.isPresent()) throw  new ResponseException(Error.TOKEN_INVALID);
        redisUtils.expireValue(token, expiration, TimeUnit.SECONDS);
        return value
                .map(item -> JsonConvert.convertJsonToObject(item,UserDetailsImpl.class)).get();
    }
}

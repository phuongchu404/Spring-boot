package com.oauth2.securityoauth.security;

import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        return Optional.ofNullable(redisUtils.getValue(token))
                .map(value -> UserDetailsImpl.class.cast(value))
                .orElseThrow(() -> new ResponseException(Error.TOKEN_INVALID));
    }
}

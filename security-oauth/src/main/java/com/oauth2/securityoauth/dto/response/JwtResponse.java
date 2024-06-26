package com.oauth2.securityoauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    //private String email;
    private Set<String> roles;
}

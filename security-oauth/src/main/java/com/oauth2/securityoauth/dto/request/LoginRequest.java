package com.oauth2.securityoauth.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

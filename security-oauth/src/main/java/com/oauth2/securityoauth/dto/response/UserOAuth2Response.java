package com.oauth2.securityoauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;
@Data
public class UserOAuth2Response {

    private Long id;

    private String email;

    private Set<String> roles;

    private Map<String, Object> attributes;

    public UserOAuth2Response(Long id, String email, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}

package com.oauth2.securityoauth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MfaVerificationResponse {
    private String username;
    private String jwt;
    private boolean mfaRequired;
    private boolean authValid;
    private boolean tokenValid;
    private String message;
}

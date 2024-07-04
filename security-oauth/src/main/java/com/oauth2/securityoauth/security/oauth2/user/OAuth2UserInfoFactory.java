package com.oauth2.securityoauth.security.oauth2.user;

import com.oauth2.securityoauth.consts.Provider;
import com.oauth2.securityoauth.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(Provider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase(Provider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(Provider.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        }else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}

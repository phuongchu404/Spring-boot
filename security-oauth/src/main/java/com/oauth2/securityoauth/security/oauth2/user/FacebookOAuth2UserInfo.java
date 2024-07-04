package com.oauth2.securityoauth.security.oauth2.user;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo{
    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getImageUrl() {
        if(attributes.containsKey("picture")){
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if(pictureObj.containsKey("data")){
                Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")){
                    return dataObj.get("url").toString();
                }
            }
        }
        return null;
    }
}

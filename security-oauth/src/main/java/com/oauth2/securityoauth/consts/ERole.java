package com.oauth2.securityoauth.consts;

public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");
    private    String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    ERole(String value){
        this.value = value;
    }
}

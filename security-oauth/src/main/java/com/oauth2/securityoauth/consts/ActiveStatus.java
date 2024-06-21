package com.oauth2.securityoauth.consts;

public enum ActiveStatus {
    ACTIVE(true),
    DEACTIVE(false);

    private boolean value;
    public boolean getValue() {
        return value;
    }
    ActiveStatus(boolean value) {
        this.value = value;
    }
}

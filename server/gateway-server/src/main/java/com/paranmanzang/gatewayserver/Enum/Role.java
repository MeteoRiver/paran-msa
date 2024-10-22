package com.paranmanzang.gatewayserver.Enum;

public enum Role implements CodeEnum<String> {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER"), ROLE_SELLER("ROLE_SELLER");

    private final String code;

    Role(String code) {this.code = code;}

    public String getCode(){return code;}
}
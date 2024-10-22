package com.paranmanzang.gatewayserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
    private String username;
    private String password;
    private boolean state;
}

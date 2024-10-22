package com.paranmanzang.gatewayserver.model.Domain.oauth;

import com.paranmanzang.gatewayserver.model.Domain.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserModel userModel;

    public CustomOAuth2User(UserModel userModel) {
        this.userModel = userModel;
        System.out.println("15");
    }

    @Override
    public Map<String, Object> getAttributes() {

        return Map.of(
                "username", userModel.getUsername(),
                "nickname", userModel.getNickname(),
                "name", userModel.getName(),
                "tel", userModel.getTel()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> userModel.getRole().toString()); // Role을 문자열로 변환
        return collection;
    }

    @Override
    public String getName() {
        return userModel.getName();
    }

    public String getUsername() {
        return userModel.getUsername();
    }

    public String getNickname() {
        return userModel.getNickname();
    }
}
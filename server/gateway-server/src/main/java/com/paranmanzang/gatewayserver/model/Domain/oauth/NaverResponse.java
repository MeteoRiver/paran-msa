package com.paranmanzang.gatewayserver.model.Domain.oauth;

import java.util.Collections;
import java.util.Map;

public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {

        // Ensure "response" key exists and is a Map
        this.attribute = (attribute != null && attribute.containsKey("response"))
                ? (Map<String, Object>) attribute.get("response")
                : Collections.emptyMap(); // Use emptyMap() to avoid null handling
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        Object id = attribute.get("id");
        return id != null ? id.toString() : null;
    }

    @Override
    public String getName() {
        Object name = attribute.get("name");
        return name != null ? name.toString() : null;
    }

    @Override
    public String getMobile() {
        Object mobile = attribute.get("mobile");
        return mobile != null ? mobile.toString() : null;
    }

}
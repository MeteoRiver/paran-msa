package com.paranmanzang.gatewayserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "uri")
public class UriConfiguration {
    private String chatServiceUri;
    private String commentServiceUri;
    private String groupServiceUri;
    private String fileServiceUri;
    private String roomServiceUri;
    private String userServiceUri;

    // Getters and Setters
    public String getChatServiceUri() {
        return chatServiceUri;
    }

    public void setChatServiceUri(String chatServiceUri) {
        this.chatServiceUri = chatServiceUri;
    }

    public String getCommentServiceUri() {
        return commentServiceUri;
    }

    public void setCommentServiceUri(String commentServiceUri) {
        this.commentServiceUri = commentServiceUri;
    }

    public String getGroupServiceUri() {
        return groupServiceUri;
    }

    public void setGroupServiceUri(String groupServiceUri) {
        this.groupServiceUri = groupServiceUri;
    }

    public String getFileServiceUri() {
        return fileServiceUri;
    }

    public void setFileServiceUri(String fileServiceUri) {
        this.fileServiceUri = fileServiceUri;
    }

    public String getRoomServiceUri() {
        return roomServiceUri;
    }

    public void setRoomServiceUri(String roomServiceUri) {
        this.roomServiceUri = roomServiceUri;
    }

    public String getUserServiceUri() {
        return userServiceUri;
    }

    public void setUserServiceUri(String userServiceUri) {
        this.userServiceUri = userServiceUri;
    }
}

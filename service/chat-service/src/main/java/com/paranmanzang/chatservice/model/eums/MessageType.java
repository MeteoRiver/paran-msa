package com.paranmanzang.chatservice.model.eums;

public enum MessageType {
    ENTER("ENTER","님이 입장했습니다."),
    TALK("TALK",""),
    EXIT("EXIT","님이 나갔습니다.");

    private final String type;
    private final String message;

    MessageType(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}

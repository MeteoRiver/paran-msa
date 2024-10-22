package com.paranmanzang.fileservice.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FileType {
    USER(0, "user"),
    ROOM(1, "room"),
    ABOARD(2, "aBoard"),
    GROUP_POST(3, "groupPost"),
    BOOK(4, "book");

    private final int code;
    private final String type;

    FileType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static FileType fromCode(int code) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }


    public static FileType fromType(String type) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown type: " + type));
    }

}

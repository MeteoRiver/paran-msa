package com.paranmanzang.fileservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileModel {
    private String id;
    private String type;
    private String path;
    private long refId;
    private LocalDateTime uploadAt;
}

package com.paranmanzang.fileservice.model.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection="files")
public class File {
    @Id
    private String id;
    private int type;
    private String path;
    private Long refId;
    @CreatedDate
    private LocalDateTime uploadAt;

}

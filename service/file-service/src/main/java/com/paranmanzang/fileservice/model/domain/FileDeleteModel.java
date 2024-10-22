package com.paranmanzang.fileservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(title = "파일 삭제 모델")
public class FileDeleteModel {
    @Schema(title = "경로", description = "삭제할 파일의 경로", example = "디렉토리/파일명.확장자")
    private String path;
}

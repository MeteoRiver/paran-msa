package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@Schema(title = "공간 이용 가능한 시간 저장 정보")
public class TimeSaveModel {
    @Schema(title = "공간 id")
    private Long roomId;
    @Schema(title = "가게 여는 시간")
    private int openTime;
    @Schema(title = "가게 마지막 이용 시간", description = "HH:59까지 이용 가능합니다.")
    private int closeTime;
}

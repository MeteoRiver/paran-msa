package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "공간 이용 가능한 시간 정보")
public class TimeModel {
 @Schema(title = "시간 id")
 private Long id;
 @Schema(title = "이용 날짜")
 private String date;
 @Schema(title = "이용 시간")
 private String time;

}

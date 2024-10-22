package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "공간과 이용시간 정보")
public class RoomWTimeModel {
    @Schema(title = "공간 id")
    private Long id;
    @Schema(title = "공간 이름")
    private String name;
    @Schema(title = "최대 이용 정원")
    private int maxPeople;
    @Schema(title = "이용 금액", description = "1시간당 이용금액입니다.")
    private int price;
    @Schema(title = "오픈된 공간 여부")
    private boolean opened;
    @Schema(title = "가게 여는 시간")
    private LocalTime openTime;
    @Schema(title = "가게 마지막 이용 시간", description = "HH:59까지 이용 가능합니다.")
    private LocalTime closeTime;
    @Schema(title = "등록일")
    private LocalDateTime createdAt;
    @Schema(title = "승인 여부")
    private boolean enabled;
    @Schema(title = "등록자")
    private String nickname;
    @Schema(title = "공간 이용 시간")
    private List<?> times;
}
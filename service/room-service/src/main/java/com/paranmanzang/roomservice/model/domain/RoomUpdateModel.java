package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "공간 수정 정보")
public class RoomUpdateModel {
    @Schema(title = "공간 id")
    @NotNull
    private Long id;
    @Schema(title = "공간 이름")
    @NotBlank
    private String name;
    @Schema(title = "최대 이용 정원")
    @NotNull
    @Positive
    private int maxPeople;
    @Schema(title = "이용 금액", description = "1시간당 이용금액입니다.")
    @NotNull
    @Positive
    private int price;
    @Schema(title = "오픈된 공간 여부")
    @NotNull
    private boolean opened;
    @Schema(title = "가게 여는 시간")
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @Schema(title = "가게 마지막 이용 시간", description = "HH:59까지 이용 가능합니다.")
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;
}
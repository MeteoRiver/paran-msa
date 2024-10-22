package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리뷰 정보")
public class ReviewModel {
    @Schema(title = "리뷰 id")
    private Long id;
    @Schema(title = "별점")
    @NotNull
    @Positive(message = "자연수만 입력 가능합니다.")
    @Max(value = 10, message = "최대 입력값은 10 입니다.")
    private int rating;
    @Schema(title = "리뷰 내용")
    @NotBlank(message = "공백은 입력 불가합니다.")
    private String content;
    @Schema(title = "작성자")
    @NotBlank
    private String nickname;
    @Schema(title = "작성일")
    private LocalDateTime createAt;
    @Schema(title = "이용 공간 id")
    @NotNull
    @Positive
    private Long roomId;
    @Schema(title = "예약 정보 id")
    @NotNull
    @Positive
    private Long bookingId;
}

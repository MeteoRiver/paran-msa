package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리뷰 수정 정보")
public class ReviewUpdateModel {
    @Schema(title = "리뷰 id")
    @NotNull
    private Long id;
    @Schema(title = "별점")
    @NotNull
    @Positive(message = "자연수만 입력 가능합니다.")
    @Max(value = 10, message = "최대 입력값은 10 입니다.")
    private int rating;
    @Schema(title = "리뷰 내용")
    @NotBlank(message = "공백은 입력 불가합니다.")
    private String content;

}

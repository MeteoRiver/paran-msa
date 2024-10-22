package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Schema(title = "주소 정보")
public class AddressModel {
    @Schema(title = "주소 id")
    private Long id;
    @Schema(title = "전체 주소")
    @NotBlank(message = "공백은 입력 불가합니다.")
    private String address;
    @Schema(title = "상세 주소")
    @NotBlank(message = "공백은 입력 불가합니다.")
    private String detailAddress;
    @Schema(title = "위도")
    @NotNull
    @Digits(integer = 2, fraction = 13, message = "위도의 범위를 벗어났습니다.")
    private Double latitude;
    @Schema(title = "경도")
    @NotNull
    @Digits(integer = 3, fraction = 13, message = "경도의 범위를 벗어났습니다.")
    private Double longitude;
    @Schema(title = "공간 id")
    @NotNull
    @Positive
    private Long roomId;
}

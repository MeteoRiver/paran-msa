package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "주소 수정 정보")
public class AddressUpdateModel {
    @NotNull
    @Schema(title = "주소 id")
    private Long id;
    @NotBlank(message = "공백은 입력 불가합니다.")
    @Schema(title = "전체 주소")
    private String address;
    @NotBlank(message = "공백은 입력 불가합니다.")
    @Schema(title = "상세 주소")
    private String detailAddress;
    @NotNull
    @Digits(integer = 2, fraction = 13, message = "위도의 범위를 벗어났습니다.")
    @Schema(title = "위도")
    private Double latitude;
    @NotNull
    @Digits(integer = 3, fraction = 13, message = "경도의 범위를 벗어났습니다.")
    @Schema(title = "경도")
    private Double longitude;

}

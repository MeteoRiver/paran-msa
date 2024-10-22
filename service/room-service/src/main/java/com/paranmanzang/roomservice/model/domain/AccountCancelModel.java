package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(title = "결제 취소 정보")
public class AccountCancelModel {
    @Schema(title = "결제번호")
    private String paymentKey;
    @Schema(title = "취소 사유")
    private String cancelReason;
}

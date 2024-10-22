package com.paranmanzang.roomservice.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(title = "결제 정보")
public class AccountResultModel {
    @Schema(title = "주문 번호")
    private String orderId;
    @Schema(title = "결제 번호")
    private String paymentKey;
    @Schema(title = "결제 금액")
    private int amount;
    @Schema(title = "상품명")
    private String orderName;
    @Schema(title = "소모임 id", description = "결제한 주체입니다.")
    private Long groupId;
    @Schema(title = "공간 id", description = "사용처 입니다.")
    private Long roomId;
    @Schema(title = "예약 id")
    private Long bookingId;
    @Schema(title = "사용한 포인트")
    private int usePoint;
}

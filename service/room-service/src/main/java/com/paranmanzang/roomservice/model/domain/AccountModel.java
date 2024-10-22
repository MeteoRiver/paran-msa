package com.paranmanzang.roomservice.model.domain;

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
public class AccountModel {
    private String orderId;
    private String orderName;
    private int amount;
    private int amountTaxFree;
    private int usePoint;
    private boolean canceled;
    private String reason;
    private LocalDateTime createAt;

    private Long groupId;
    private Long roomId;
    private Long bookingId;
}

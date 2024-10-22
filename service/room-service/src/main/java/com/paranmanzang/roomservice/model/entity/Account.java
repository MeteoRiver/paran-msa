package com.paranmanzang.roomservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    private String orderId;
    @Column(nullable = false)
    private String detail;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private int amountTaxFree;
    @Column(nullable = false)
    private String payToken;
    private int usePoint;
    @ColumnDefault("false")
    private boolean canceled;
    private String reason;
    @CreatedDate
    private LocalDateTime createAt;
    @Column(nullable = false)
    private Long groupId;
    @Column(nullable = false)
    private Long roomId;
    @Column(nullable = false)
    private Long bookingId;
}

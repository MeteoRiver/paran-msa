package com.paranmanzang.roomservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int maxPeople;
    @Column(nullable = false)
    private boolean opened;
    @ColumnDefault("false")
    private boolean enabled;
    @Column(nullable = false)
    private LocalTime openTime;
    @Column(nullable = false)
    private LocalTime closeTime;
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String nickname;

    private LocalDateTime responseAt;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Time> times;
    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Review> reviews;
    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Booking> bookings;
}

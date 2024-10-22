package com.paranmanzang.roomservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT")
    private boolean enabled;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Long groupId;
    @CreatedDate
    private LocalDateTime createAt;
    private LocalDateTime responseAt;

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Time> times;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;
    @OneToOne(mappedBy = "booking")
    private Review reviews;
}

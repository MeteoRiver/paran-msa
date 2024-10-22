package com.paranmanzang.groupservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "joining")
@EntityListeners(AuditingEntityListener.class)
public class Joining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT")
    private boolean enabled;

    @Column(nullable = false)
    private String nickname;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate requestAt;

    @LastModifiedDate
    @Column
    private LocalDate responseAt;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "group_id")
    private Group group;
}

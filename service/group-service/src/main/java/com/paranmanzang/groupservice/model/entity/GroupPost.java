package com.paranmanzang.groupservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class GroupPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    //@Builder Default VS
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDate modifyAt;

    @Column(nullable = true)
    private String postCategory;

    @ColumnDefault("0")
    private int viewCount;

    @Column(nullable = true)
    private String nickname;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "book_id")//모임장만 등록가능해서 true 로바꿈
    private Book book;
}
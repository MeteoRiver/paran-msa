package com.paranmanzang.commentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @ColumnDefault("0")
    private int step;
    @ColumnDefault("0")
    private int depth;
    @ColumnDefault("0")
    private int ref;
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Long postId;

}

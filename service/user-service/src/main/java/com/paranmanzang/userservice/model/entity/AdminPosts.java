package com.paranmanzang.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="admin_posts")
public class AdminPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "modify_at", nullable = false)
    private LocalDateTime modifyAt;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String nickname;

    @PrePersist
    public void prePersist() {
        if (createAt == null) {
            createAt = LocalDateTime.now();
        }
        if (modifyAt == null) {
            modifyAt = LocalDateTime.now();
        }
        if(viewCount == null) {
            viewCount = 0L;
        }
    }

    @PreUpdate
    public void preUpdate() {
        modifyAt = LocalDateTime.now();
    }
}

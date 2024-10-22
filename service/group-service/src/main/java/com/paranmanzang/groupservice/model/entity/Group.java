package com.paranmanzang.groupservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`groups`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;

    private String categoryName;

    @Builder.Default
    @Column(nullable = false , updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt =  LocalDateTime.now();

    @ColumnDefault("false")
    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean enabled;

    @Builder.Default
    @Column(nullable = false)
    private String detail = "'Empty detail'";

    @Column(nullable = false)
    private String nickname;//관리자 nickname

    @Builder.Default
    @Column(nullable = false)
    private String chatRoomId = "-1";//채팅방Id

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Joining> joinings = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<GroupPost> groupPosts = new ArrayList<>();

    public Group(Long id) {
        this.id = id;
    }
}
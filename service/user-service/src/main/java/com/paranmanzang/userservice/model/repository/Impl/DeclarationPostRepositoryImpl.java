package com.paranmanzang.userservice.model.repository.Impl;


import com.paranmanzang.userservice.model.domain.AdminPostModel;
import com.paranmanzang.userservice.model.domain.DeclarationPostModel;
import com.paranmanzang.userservice.model.entity.DeclarationPosts;
import com.paranmanzang.userservice.model.entity.QDeclarationPosts;
import com.paranmanzang.userservice.model.repository.custom.DeclarationPostRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RequiredArgsConstructor
public class DeclarationPostRepositoryImpl implements DeclarationPostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QDeclarationPosts declarationPosts = QDeclarationPosts.declarationPosts;

    // declarer에 따라 게시물을 찾는 메서드
    @Override
    public Page<DeclarationPostModel> findByNickname(String nickname, Pageable pageable) {
        // DeclarationPosts의 ID만 선택하여 리스트로 저장
        List<Long> declarationpostsIdsn = jpaQueryFactory
                .select(declarationPosts.id)  // ID만 선택
                .from(declarationPosts)
                .where(declarationPosts.declarer.eq(nickname))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // ID 리스트가 비어 있지 않으면 데이터를 조회
        List<DeclarationPostModel> declarationPostModeln = declarationpostsIdsn.isEmpty() ? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                DeclarationPostModel.class,
                                declarationPosts.id,
                                declarationPosts.title,
                                declarationPosts.content,
                                declarationPosts.target,
                                declarationPosts.declarer,
                                declarationPosts.createdAt
                        ))
                        .from(declarationPosts)
                        .where(declarationPosts.id.in(declarationpostsIdsn))  // ID 리스트 사용
                        .fetch();

        // 결과를 Page 객체로 반환
        return new PageImpl<>(declarationPostModeln, pageable, declarationpostsIdsn.size());
    }

    // 모든 게시물을 찾는 메서드
    @Override
    public Page<DeclarationPostModel> findAllPost(Pageable pageable) {
        // DeclarationPosts의 ID만 선택하여 리스트로 저장
        List<Long> declarationpostsIds = jpaQueryFactory
                .select(declarationPosts.id)  // ID만 선택
                .from(declarationPosts)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // ID 리스트가 비어 있지 않으면 데이터를 조회
        List<DeclarationPostModel> declarationPostModelList = declarationpostsIds.isEmpty() ? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                DeclarationPostModel.class,
                                declarationPosts.id,
                                declarationPosts.title,
                                declarationPosts.content,
                                declarationPosts.target,
                                declarationPosts.declarer,
                                declarationPosts.createdAt
                        ))
                        .from(declarationPosts)
                        .where(declarationPosts.id.in(declarationpostsIds))  // ID 리스트 사용
                        .fetch();

        // 결과를 Page 객체로 반환
        return new PageImpl<>(declarationPostModelList, pageable, declarationpostsIds.size());
    }
}

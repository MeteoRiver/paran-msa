package com.paranmanzang.userservice.model.repository.Impl;


import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.entity.Friends;
import com.paranmanzang.userservice.model.entity.QFriends;
import com.paranmanzang.userservice.model.repository.custom.FriendRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    //친구 목록 유저 이름으로 찾기
    @Override
    public List<FriendModel> findFriendByRequestUser(String nickname) {
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(nickname)
                        .or(friends.responseUser.eq(nickname))
                )
                .fetch();

        return friendIds.isEmpty()? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                FriendModel.class,
                                friends.id,
                                friends.responseUser,
                                friends.requestUser,
                                friends.request_at,
                                friends.response_at))
                        .from(friends)
                        .where(friends.id.in(friendIds))
                        .fetch();
    }
    //친구 요청목록 유저 이름으로 찾기
    @Override
    public List<FriendModel> findFriendRequestByRequestUser(String nickname) {
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(nickname)
                        .or(friends.responseUser.eq(nickname))
                )
                .fetch();

        return friendIds.isEmpty()? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                FriendModel.class,
                                friends.id,
                                friends.responseUser,
                                friends.requestUser,
                                friends.request_at,
                                friends.response_at))
                        .from(friends)
                        .where(friends.id.in(friendIds))
                        .fetch();
    }

    @Override
    public Friends findFriendRequestByRequestUserAndResponseUser(String nickname1, String nickname2) {
        QFriends friends = QFriends.friends;

        // 친구 요청의 id 목록을 먼저 찾기
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(
                        (friends.requestUser.eq(nickname1).and(friends.responseUser.eq(nickname2)))
                                .or(friends.requestUser.eq(nickname2).and(friends.responseUser.eq(nickname1)))
                                .and(friends.response_at.isNull())  // 응답 시간이 NULL인 조건
                )
                .fetch();

        // 친구 요청이 없으면 null 반환
        if (friendIds.isEmpty()) {
            return null;  // 혹은 적절한 예외를 던질 수도 있습니다.
        }

        // 친구 요청 정보 조회하여 반환
        return jpaQueryFactory
                .select(friends)
                .from(friends)
                .where(friends.id.eq(friendIds.get(0)))  // 첫 번째 ID로 친구 요청 조회
                .fetchOne();  // 단일 결과 반환
    }

    //친구 목록 둘 다 체크
    @Override
    public Boolean existsByRequestUserAndResponseUser(String requestUser, String responseUser){
        log.info("Request User: {}, Response User: {}", requestUser, responseUser);
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(requestUser)
                        .and(friends.responseUser.eq(responseUser))
                        .and(friends.response_at.isNotNull())
                ).fetch();
        System.out.println(friendIds.size());
        System.out.println(friendIds);
        System.out.println(friendIds.isEmpty());
        return !friendIds.isEmpty();
    }
    //친구 요청 목록 둘 다 체크
    @Override
    public Boolean existsRequestByRequestUserAndResponseUser(String requestUser, String responseUser){
        log.info("Request User: {}, Response User: {}", requestUser, responseUser);
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(requestUser)
                        .and(friends.responseUser.eq(responseUser))
                        .and(friends.response_at.isNull())
                ).fetch();
        System.out.println(friendIds.size());
        System.out.println(friendIds);
        System.out.println(friendIds.isEmpty());
        return !friendIds.isEmpty();
    }
}

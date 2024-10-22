package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.LikeRoomModel;
import com.paranmanzang.roomservice.model.entity.QLikeRooms;
import com.paranmanzang.roomservice.model.repository.LikeRoomRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LikeRoomRepositoryImpl implements LikeRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LikeRoomModel> findLikeIdByNickname(String nickname) {
        QLikeRooms likeRooms = QLikeRooms.likeRooms;
        //id
        var likeRoomIds = jpaQueryFactory
                .select(likeRooms.id)
                .from(likeRooms)
                .where(likeRooms.nickname.eq(nickname))
                .fetch();
        //리스트
        return likeRoomIds.isEmpty() ? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(LikeRoomModel.class,
                                likeRooms.id,
                                likeRooms.roomId,
                                likeRooms.nickname
                        ))
                        .from(likeRooms)
                        .where(likeRooms.id.in(likeRoomIds))
                        .fetch();


    }
}


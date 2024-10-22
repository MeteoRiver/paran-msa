package com.paranmanzang.roomservice.model.repository.impl;

import com.paranmanzang.roomservice.model.domain.RoomModel;
import com.paranmanzang.roomservice.model.entity.QRoom;
import com.paranmanzang.roomservice.model.entity.Room;
import com.paranmanzang.roomservice.model.repository.RoomCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QRoom room = QRoom.room;

    @Override
    public Page<RoomModel> findByNickname(String nickname, Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                RoomModel.class,
                                room.id.as("id"),
                                room.name.as("name"),
                                room.maxPeople.as("maxPeople"),
                                room.price.as("price"),
                                room.opened.as("opened"),
                                room.openTime.as("openTime"),
                                room.closeTime.as("closeTime"),
                                room.createdAt.as("createdAt"),
                                room.enabled.as("enabled"),
                                room.nickname.as("nickname")
                        )
                )
                .from(room)
                .where(room.nickname.in(
                                jpaQueryFactory.select(room.nickname).from(room)
                                        .where(room.nickname.eq(nickname))
                                        .limit(pageable.getPageSize())
                                        .offset(pageable.getOffset())
                                        .fetch()
                        )
                )
                .fetch();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public Page<RoomModel> findByPagination(Pageable pageable) {
        var result= jpaQueryFactory.select(
                        Projections.constructor(
                                RoomModel.class,
                                room.id.as("id"),
                                room.name.as("name"),
                                room.maxPeople.as("maxPeople"),
                                room.price.as("price"),
                                room.opened.as("opened"),
                                room.openTime.as("openTime"),
                                room.closeTime.as("closeTime"),
                                room.createdAt.as("createdAt"),
                                room.enabled.as("enabled"),
                                room.nickname.as("nickname")
                        )
                )
                .from(room)
                .where(room.id.in(
                        jpaQueryFactory.select(room.id)
                                .from(room)
                                .limit(pageable.getPageSize())
                                .offset(pageable.getOffset())
                                .fetch()
                ))
                .fetch().stream().toList();
        return new PageImpl<>( result, pageable, result.size());
    }

    @Override
    public List<Room> findAllByNickname(String nickname) {
        return jpaQueryFactory.selectFrom(room)
                .where(room.nickname.eq(nickname))
                .fetch();
    }
}

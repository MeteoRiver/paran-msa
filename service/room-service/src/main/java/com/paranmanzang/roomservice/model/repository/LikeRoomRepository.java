package com.paranmanzang.roomservice.model.repository;

import com.paranmanzang.roomservice.model.entity.LikeRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRoomRepository extends JpaRepository<LikeRooms,Long>, LikeRoomRepositoryCustom {
    // 사용자 닉네임과 룸 ID로 LikeRooms 조회
    LikeRooms findByNicknameAndRoomId(String nickname, Long roomId);

    // 사용자 낙네임과 룸 ID로 LikeRoom 삭제
    int deleteByNicknameAndRoomId(String nickname, Long roomId);

    // 사용자 닉네임과 룸 ID로 Likeroom 조회
    Boolean existsByNicknameAndRoomId(String nickname, Long roomId);

}

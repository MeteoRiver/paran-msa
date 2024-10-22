package com.paranmanzang.roomservice.service.impl;



import com.paranmanzang.roomservice.model.domain.LikeRoomModel;
import com.paranmanzang.roomservice.model.entity.LikeRooms;
import com.paranmanzang.roomservice.model.repository.LikeRoomRepository;
import com.paranmanzang.roomservice.service.LikeRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LikeRoomServiceImpl implements LikeRoomService {
    private final LikeRoomRepository likeRoomRepository;

    public LikeRoomServiceImpl(LikeRoomRepository likeRoomRepository ) {
        this.likeRoomRepository = likeRoomRepository;
    }

    @Override
    public List<LikeRoomModel> findAllByUserNickname(String userNickname) {
        return likeRoomRepository.findLikeIdByNickname(userNickname);
    }


    @Override
    public Object insert(LikeRoomModel likeRoomModel) {
        String nickname = likeRoomModel.getNickname();
        Long roomId = likeRoomModel.getRoomId();
        System.out.println("서비스");

        try {
            if (likeRoomRepository.existsByNicknameAndRoomId(nickname, roomId)) {
                return false; // 이미 좋아요를 눌렀으면 false 반환
            }

            LikeRooms likeRooms = new LikeRooms();
            likeRooms.setRoomId(roomId);
            likeRooms.setNickname(nickname);
            return LikeRoomModel.fromEntity(likeRoomRepository.save(likeRooms));

        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;

        } catch (IllegalArgumentException e) {
            // 비즈니스 로직 예외 처리
            System.err.println("비즈니스 로직 오류 발생: " + e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean remove(LikeRoomModel likeRoomModel) {
        try {
            LikeRooms likeRooms = likeRoomRepository.findByNicknameAndRoomId(likeRoomModel.getNickname(), likeRoomModel.getRoomId());
            if (likeRooms != null) {
                likeRoomRepository.deleteByNicknameAndRoomId(likeRoomModel.getNickname(), likeRoomModel.getRoomId());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

}

package com.paranmanzang.groupservice.service;

import com.paranmanzang.groupservice.model.domain.BookResponseModel;
import com.paranmanzang.groupservice.model.domain.LikeBookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindException;

import java.util.List;

public interface LikeBookService {
    //책 좋아요
    Object add(LikeBookModel likeBooKModel) throws BindException;

    //좋아요 취소
    Boolean remove(LikeBookModel likeBooKModel) throws BindException;

    //마이페이지 책 찜 조회
    List<BookResponseModel> findAllByNickname(String nickname);
}

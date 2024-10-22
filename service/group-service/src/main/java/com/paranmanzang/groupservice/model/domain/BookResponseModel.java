package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@NoArgsConstructor
public class BookResponseModel {
    private Long id;
    private String title;
    private String author;

    private String categoryName;
    private int likeBookCount;

    public static BookResponseModel fromEntity(Book book) {
        return BookResponseModel.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .categoryName(book.getCategoryName())
                .likeBookCount(book.getLike_books() != null ? book.getLike_books().size() : 0)
                .build();
    }

    public BookResponseModel(Long id, String title, String author, String categoryName, int likeBookCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryName = categoryName;
        this.likeBookCount = likeBookCount;
    }
}
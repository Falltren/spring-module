package com.fallt.spring_data_jdbc.domain.entity;

import com.fallt.spring_data_jdbc.domain.dto.request.UpsertBookRequest;
import com.fallt.spring_data_jdbc.domain.dto.response.BookResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "books")
public class Book {

    @Id
    private Long id;
    private String title;
    private String author;

    @Column("publication_year")
    private Integer publicationYear;

    public static Book toEntity(UpsertBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        return book;
    }

    public BookResponse toResponse() {
        return BookResponse.builder()
                .title(this.getTitle())
                .author(this.getAuthor())
                .publicationYear(this.getPublicationYear())
                .build();
    }

    public void updateNotNullField(UpsertBookRequest request) {
        if (request.getTitle() != null) {
            this.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            this.setAuthor(request.getAuthor());
        }
        if (request.getPublicationYear() != null) {
            this.setPublicationYear(request.getPublicationYear());
        }
    }
}


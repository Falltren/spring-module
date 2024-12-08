package com.fallt.spring_data_jdbc;

import com.fallt.spring_data_jdbc.domain.dto.request.UpsertBookRequest;
import com.fallt.spring_data_jdbc.domain.dto.response.BookResponse;

public final class TestConstant {

    public static final String COMMON_BOOK_PATH = "/api/v1/books";
    public static final String ID_PATH = "/api/v1/books/{id}";
    public static final BookResponse FIRST_BOOK_RESPONSE = BookResponse.builder()
            .title("Book 1")
            .author("Author 1")
            .publicationYear(2020)
            .build();
    public static final BookResponse SECOND_BOOK_RESPONSE = BookResponse.builder()
            .title("Book 2")
            .author("Author 2")
            .publicationYear(2021)
            .build();
    public static final UpsertBookRequest REQUEST = UpsertBookRequest.builder()
            .title("New title")
            .author("New author")
            .publicationYear(2022)
            .build();
}

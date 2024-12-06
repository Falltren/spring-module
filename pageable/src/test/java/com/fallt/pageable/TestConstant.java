package com.fallt.pageable;

import com.fallt.pageable.domain.dto.response.BookResponse;

import java.time.LocalDate;

public final class TestConstant {

    public static final String BOOK_PATH = "/api/v1/books";
    public static final String BOOK_PATH_WITH_ID = "/api/v1/books/{id}";
    public static final BookResponse FIRST_BOOK_RESPONSE = BookResponse.builder()
            .title("Title1")
            .genre("ROMANCE")
            .author("John Doe")
            .publicationDate(LocalDate.of(1990, 10, 20))
            .build();
    public static final BookResponse SECOND_BOOK_RESPONSE = BookResponse.builder()
            .title("Title2")
            .genre("THRILLER")
            .author("John Doe")
            .publicationDate(LocalDate.of(1992, 6, 15))
            .build();
    public static final BookResponse THIRD_BOOK_RESPONSE = BookResponse.builder()
            .title("Title3")
            .genre("ROMANCE")
            .author("John Doe")
            .publicationDate(LocalDate.of(1993, 4, 22))
            .build();
    public static final BookResponse FOURTH_BOOK_RESPONSE = BookResponse.builder()
            .title("Title4")
            .genre("MYSTERY")
            .author("Jacoco")
            .publicationDate(LocalDate.of(1995, 10, 10))
            .build();
    public static final BookResponse FIFTH_BOOK_RESPONSE = BookResponse.builder()
            .title("Title5")
            .genre("HORROR")
            .author("Jacoco")
            .publicationDate(LocalDate.of(2000, 7, 14))
            .build();
}

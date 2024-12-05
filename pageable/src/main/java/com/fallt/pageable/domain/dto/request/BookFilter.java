package com.fallt.pageable.domain.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookFilter {

    private Integer offset;
    private Integer limit;
    private String genre;
    private String author;
    private LocalDate from;
    private LocalDate to;
}

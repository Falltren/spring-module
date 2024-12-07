package com.fallt.pageable.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFilter {

    private Integer offset;
    private Integer limit;
    private String genre;
    private String author;
    private LocalDate from;
    private LocalDate to;
}

package com.fallt.pageable.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertBookRequest {

    private String title;

    @JsonAlias("published_date")
    private LocalDate publishedDate;
    private String genre;

    @JsonAlias("author_id")
    private Long authorId;
}

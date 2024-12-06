package com.fallt.pageable.domain.dto.request;

import com.fallt.pageable.domain.entity.enums.Genre;
import com.fallt.pageable.validation.GenreValidation;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertBookRequest {

    private String title;

    @JsonAlias("publication_date")
    private String publicationDate;

    @GenreValidation(enumClass = Genre.class)
    private String genre;

    @JsonAlias("author_id")
    private Long authorId;
}

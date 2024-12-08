package com.fallt.spring_data_jdbc.domain.dto.request;

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
    private String author;

    @JsonAlias("publication_year")
    private Integer publicationYear;
}

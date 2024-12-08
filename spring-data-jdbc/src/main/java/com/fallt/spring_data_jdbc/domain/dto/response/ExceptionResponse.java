package com.fallt.spring_data_jdbc.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

    private String message;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();
}

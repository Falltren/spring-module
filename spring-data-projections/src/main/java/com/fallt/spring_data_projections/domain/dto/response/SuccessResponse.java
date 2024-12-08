package com.fallt.spring_data_projections.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse {

    @Builder.Default
    private boolean success = true;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();
}

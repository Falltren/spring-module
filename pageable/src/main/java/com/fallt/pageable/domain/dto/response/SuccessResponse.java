package com.fallt.pageable.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessResponse {

    @Builder.Default
    private boolean success = true;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();
}

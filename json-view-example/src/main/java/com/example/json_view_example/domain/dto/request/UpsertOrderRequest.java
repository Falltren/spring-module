package com.example.json_view_example.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertOrderRequest {

    private List<String> products;
    private String status;
    private BigDecimal cost;
    private Long userId;

}

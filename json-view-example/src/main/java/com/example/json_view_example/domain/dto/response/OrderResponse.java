package com.example.json_view_example.domain.dto.response;

import com.example.json_view_example.controller.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    @Builder.Default
    @JsonView(Views.UserSummary.class)
    private List<String> products = new ArrayList<>();

    @JsonView(Views.UserSummary.class)
    private String status;

    @JsonView(Views.UserSummary.class)
    private String cost;

}

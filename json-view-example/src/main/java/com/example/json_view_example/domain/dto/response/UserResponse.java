package com.example.json_view_example.domain.dto.response;

import com.example.json_view_example.controller.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    @JsonView(Views.UserDetails.class)
    private String username;

    @JsonView(Views.UserDetails.class)
    private String email;

    @JsonView(Views.UserSummary.class)
    private List<OrderResponse> orders;

}

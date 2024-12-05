package com.example.json_view_example;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.dto.response.SuccessResponse;

public final class TestConstant {

    public static final String COMMON_USER_PATH = "/api/v1/users";
    public static final String USER_PATH_WITH_ID = "/api/v1/users/{id}";
    public static final String USERNAME = "tom";
    public static final String EMAIL = "tom@tom.com";
    public static final UpsertUserRequest CORRECT_REQUEST = UpsertUserRequest.builder()
            .username(USERNAME)
            .email(EMAIL)
            .build();
    public static final SuccessResponse SUCCESS_RESPONSE = SuccessResponse.builder().build();
}

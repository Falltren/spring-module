package com.example.json_view_example;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.dto.response.SuccessResponse;
import com.example.json_view_example.domain.entity.User;

public final class TestConstant {

    public static final String COMMON_USER_PATH = "/api/v1/users";
    public static final String USER_PATH_WITH_ID = "/api/v1/users/{id}";
    public static final String FIRST_USERNAME = "tom";
    public static final String FIRST_EMAIL = "tom@tom.com";
    public static final String SECOND_USERNAME = "jerry";
    public static final String SECOND_EMAIL = "jerry@jerry.com";
    public static final Long ID = 1L;
    public static final User FIRST_USER = User.builder()
            .id(1L)
            .username(FIRST_USERNAME)
            .email(FIRST_EMAIL)
            .build();
    public static final User SECOND_USER = User.builder()
            .id(2L)
            .username(SECOND_USERNAME)
            .email(SECOND_EMAIL)
            .build();
    public static final UpsertUserRequest CORRECT_REQUEST = UpsertUserRequest.builder()
            .username(FIRST_USERNAME)
            .email(FIRST_EMAIL)
            .build();
    public static final SuccessResponse SUCCESS_RESPONSE = SuccessResponse.builder().build();
}

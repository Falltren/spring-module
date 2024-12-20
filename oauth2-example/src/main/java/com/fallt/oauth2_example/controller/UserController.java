package com.fallt.oauth2_example.controller;

import com.fallt.oauth2_example.domain.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/user")
    public UserResponse user(@AuthenticationPrincipal OAuth2User principal) {
        return UserResponse.builder()
                .name(principal.getAttribute("name"))
                .email(principal.getAttribute("email"))
                .build();
    }

    @GetMapping("/admin")
    public String hello() {
        return "Hello admin!";
    }
}

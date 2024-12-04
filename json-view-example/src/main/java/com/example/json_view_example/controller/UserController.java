package com.example.json_view_example.controller;

import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.service.UserService;
import com.example.json_view_example.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @JsonView(Views.UserDetails.class)
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}

package com.example.json_view_example.controller;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.service.UserService;
import com.example.json_view_example.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    @JsonView(Views.UserSummary.class)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User create(@RequestBody UpsertUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UpsertUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}

package com.example.json_view_example.service;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.exception.EntityNotFoundException;
import com.example.json_view_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return getUser(id);
    }

    public User create(UpsertUserRequest request){

    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with ID: {0} not found", id)));
    }
}

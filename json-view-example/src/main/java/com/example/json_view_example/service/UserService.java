package com.example.json_view_example.service;

import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }
}

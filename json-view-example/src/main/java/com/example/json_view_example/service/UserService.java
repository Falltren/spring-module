package com.example.json_view_example.service;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.exception.EntityNotFoundException;
import com.example.json_view_example.mapper.UserMapper;
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

    public User create(UpsertUserRequest request) {
        User user = UserMapper.INSTANCE.toEntity(request);
        return userRepository.save(user);
    }

    public User update(Long id, UpsertUserRequest request) {
        User existedUser = getUser(id);
        UserMapper.INSTANCE.updateUserFromDto(request, existedUser);
        return userRepository.save(existedUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("User with ID: {0} not found", id)));
    }
}

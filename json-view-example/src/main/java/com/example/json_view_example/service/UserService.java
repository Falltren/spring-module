package com.example.json_view_example.service;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.dto.response.SuccessResponse;
import com.example.json_view_example.domain.dto.response.UserResponse;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.exception.AlreadyExistException;
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

    public List<UserResponse> getAll() {
        return UserMapper.INSTANCE.toListResponse(userRepository.findAll());
    }

    public UserResponse getUserById(Long id) {
        return UserMapper.INSTANCE.toResponse(getUser(id));
    }

    public SuccessResponse create(UpsertUserRequest request) {
        isExistedEmail(request.getEmail());
        User user = UserMapper.INSTANCE.toEntity(request);
        userRepository.save(user);
        return SuccessResponse.builder().build();
    }

    public SuccessResponse update(Long id, UpsertUserRequest request) {
        isExistedEmail(request.getEmail());
        User existedUser = getUser(id);
        UserMapper.INSTANCE.updateUserFromDto(request, existedUser);
        userRepository.save(existedUser);
        return SuccessResponse.builder().build();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("User with ID: {0} not found", id)));
    }

    private void isExistedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistException(MessageFormat.format("User with email: {0} already exists", email));
        }
    }

}

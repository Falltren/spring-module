package com.fallt.jwt.service;

import com.fallt.jwt.domain.dto.request.UpsertUserRequest;
import com.fallt.jwt.domain.dto.response.UserResponse;
import com.fallt.jwt.domain.entity.User;
import com.fallt.jwt.exception.AlreadyExistException;
import com.fallt.jwt.exception.EntityNotFoundException;
import com.fallt.jwt.mapper.UserMapper;
import com.fallt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(Long id) {
        User user = getUser(id);
        return UserMapper.INSTANCE.toResponse(user);
    }

    public UserResponse createUser(UpsertUserRequest request) {
        checkExistingUser(request.getUsername());
        User user = UserMapper.INSTANCE.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return UserMapper.INSTANCE.toResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UpsertUserRequest request) {
        if (request.getUsername() != null) {
            checkExistingUser(request.getUsername());
        }
        User user = getUser(id);
        UserMapper.INSTANCE.updateUserFromDto(request, user);
        return UserMapper.INSTANCE.toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException(MessageFormat.format("User with ID: {0} not found", id));
        }
        return optionalUser.get();
    }

    private void checkExistingUser(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistException(MessageFormat.format("User with name: {0} already exists", username));
        }
    }
}

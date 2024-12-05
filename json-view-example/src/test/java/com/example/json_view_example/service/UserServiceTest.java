package com.example.json_view_example.service;

import com.example.json_view_example.domain.dto.response.SuccessResponse;
import com.example.json_view_example.domain.dto.response.UserResponse;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.exception.AlreadyExistException;
import com.example.json_view_example.exception.EntityNotFoundException;
import com.example.json_view_example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.json_view_example.TestConstant.CORRECT_REQUEST;
import static com.example.json_view_example.TestConstant.FIRST_EMAIL;
import static com.example.json_view_example.TestConstant.FIRST_USER;
import static com.example.json_view_example.TestConstant.FIRST_USERNAME;
import static com.example.json_view_example.TestConstant.ID;
import static com.example.json_view_example.TestConstant.SECOND_EMAIL;
import static com.example.json_view_example.TestConstant.SECOND_USER;
import static com.example.json_view_example.TestConstant.SECOND_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Create user")
    void whenCreateUser_thenReturnSuccessResponse() {
        when(userRepository.existsByEmail(CORRECT_REQUEST.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(FIRST_USER);

        SuccessResponse result = userService.create(CORRECT_REQUEST);

        assertThat(result).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Create user with usable email")
    void whenCreateUserWithExistEmail_thenThrowAlreadyExistEx() {
        when(userRepository.existsByEmail(CORRECT_REQUEST.getEmail())).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> userService.create(CORRECT_REQUEST));

        assertThat(exception.getMessage()).isEqualTo("User with email: tom@tom.com already exists");
    }

    @Test
    @DisplayName("Update user")
    void whenUpdateUser_thenReturnSuccessResponse() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(FIRST_USER));
        when(userRepository.existsByEmail(CORRECT_REQUEST.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(FIRST_USER);

        SuccessResponse result = userService.update(ID, CORRECT_REQUEST);
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Update user by incorrect id")
    void whenUpdateUserByIncorrectId_thenThrowEntityNotFoundEx() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.update(ID, CORRECT_REQUEST));

        assertThat(exception.getMessage()).isEqualTo("User with ID: 1 not found");
    }

    @Test
    @DisplayName("Update user with usable email")
    void whenUpdateUserWithExistsEmail_thenThrowAlreadyExistEx() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(FIRST_USER));
        when(userRepository.existsByEmail(CORRECT_REQUEST.getEmail())).thenReturn(true);

        AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> userService.update(ID, CORRECT_REQUEST));

        assertThat(exception.getMessage()).isEqualTo("User with email: tom@tom.com already exists");
    }

    @Test
    @DisplayName("Delete user")
    void whenDelete_thenShouldDeleteUser() {
        doNothing().when(userRepository).deleteById(ID);

        userService.delete(ID);

        verify(userRepository, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("Get user by id")
    void whenGetById_thenReturnUserResponse() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(FIRST_USER));

        UserResponse response = userService.getUserById(ID);

        assertThat(response.getEmail()).isEqualTo(FIRST_EMAIL);
        assertThat(response.getUsername()).isEqualTo(FIRST_USERNAME);
    }

    @Test
    @DisplayName("Get user by incorrect id")
    void whenGetByIncorrectId_thenThrowEntityNotFoundEx() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(ID));

        assertThat(exception.getMessage()).isEqualTo("User with ID: 1 not found");
    }

    @Test
    @DisplayName("Get all users")
    void whenGetAllUsers_thenReturnListUsers() {
        List<UserResponse> expected = List.of(
                UserResponse.builder().email(FIRST_EMAIL).username(FIRST_USERNAME).orders(new ArrayList<>()).build(),
                UserResponse.builder().email(SECOND_EMAIL).username(SECOND_USERNAME).orders(new ArrayList<>()).build()
        );
        when(userRepository.findAll()).thenReturn(List.of(FIRST_USER, SECOND_USER));

        List<UserResponse> actual = userService.getAll();

        assertThat(actual).isEqualTo(expected);
    }
}

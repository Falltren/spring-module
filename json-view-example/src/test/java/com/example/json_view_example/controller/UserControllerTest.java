package com.example.json_view_example.controller;

import com.example.json_view_example.domain.dto.request.UpsertUserRequest;
import com.example.json_view_example.domain.dto.response.OrderResponse;
import com.example.json_view_example.domain.dto.response.UserResponse;
import com.example.json_view_example.exception.AlreadyExistException;
import com.example.json_view_example.exception.EntityNotFoundException;
import com.example.json_view_example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static com.example.json_view_example.TestConstant.COMMON_USER_PATH;
import static com.example.json_view_example.TestConstant.CORRECT_REQUEST;
import static com.example.json_view_example.TestConstant.FIRST_EMAIL;
import static com.example.json_view_example.TestConstant.SUCCESS_RESPONSE;
import static com.example.json_view_example.TestConstant.FIRST_USERNAME;
import static com.example.json_view_example.TestConstant.USER_PATH_WITH_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Correct user creating")
    void whenCreateUser_thenReturnCreated() throws Exception {
        when(userService.create(CORRECT_REQUEST)).thenReturn(SUCCESS_RESPONSE);

        mockMvc.perform(post(COMMON_USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CORRECT_REQUEST)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @DisplayName("Incorrect email")
    @MethodSource("provideEmailAndExpectedStatus")
    void whenCreateUserWithIncorrectEmail_thenReturnBadRequest(String email, String message) throws Exception {
        UpsertUserRequest request = UpsertUserRequest.builder()
                .username(FIRST_USERNAME)
                .email(email)
                .build();

        mockMvc.perform(post(COMMON_USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(message));
    }

    private static Stream<Arguments> provideEmailAndExpectedStatus() {
        return Stream.of(
                Arguments.of("incorrect", "Incorrect email"),
                Arguments.of("tom@user-email-with-more-than-30-characters-here.com", "The length of the email should be no more than 30 characters")
        );
    }

    @ParameterizedTest
    @DisplayName("Incorrect username")
    @MethodSource("provideUsernameAndExpectedStatus")
    void whenCreateUserWithIncorrectName_thenReturnBadRequest(String username, int expectedStatus) throws Exception {
        UpsertUserRequest request = UpsertUserRequest.builder()
                .username(username)
                .email(FIRST_EMAIL)
                .build();

        mockMvc.perform(post(COMMON_USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is(expectedStatus))
                .andExpect(jsonPath("$.message").value("The username should be between 3 and 30 characters"));
    }

    private static Stream<Arguments> provideUsernameAndExpectedStatus() {
        return Stream.of(
                Arguments.of(null, 400),
                Arguments.of("to", 400),
                Arguments.of("username_with_more_than_30_characters_here", 400)
        );
    }

    @Test
    @DisplayName("Creating a user with a usable email")
    void whenCreateUserWithExistedEmail_thenReturnBadRequest() throws Exception {
        UpsertUserRequest request = UpsertUserRequest.builder()
                .username(FIRST_USERNAME)
                .email(FIRST_EMAIL)
                .build();
        when(userService.create(request)).thenThrow(AlreadyExistException.class);

        mockMvc.perform(post(COMMON_USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Correct user updating")
    void whenUpdateUser_thenReturnOk() throws Exception {
        Long id = 1L;
        when(userService.update(id, CORRECT_REQUEST)).thenReturn(SUCCESS_RESPONSE);

        mockMvc.perform(put(USER_PATH_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CORRECT_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update user with a usable email")
    void whenUpdateUserWithExistedEmail_thenReturnBadRequest() throws Exception {
        Long id = 1L;
        when(userService.update(id, CORRECT_REQUEST)).thenThrow(AlreadyExistException.class);

        mockMvc.perform(put(USER_PATH_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CORRECT_REQUEST)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update user by incorrect id")
    void whenUpdateUserByIncorrectId_thenReturnNotFound() throws Exception {
        Long id = 1000L;
        when(userService.update(id, CORRECT_REQUEST)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put(USER_PATH_WITH_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CORRECT_REQUEST)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete user")
    void whenDeleteUser_thenReturnNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(USER_PATH_WITH_ID, id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get user by id")
    void whenGetUserById_thenReturnOk() throws Exception {
        Long id = 1L;
        UserResponse userResponse = createUserResponse();
        when(userService.getUserById(id)).thenReturn(userResponse);

        mockMvc.perform(get(USER_PATH_WITH_ID, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userResponse)));
    }

    @Test
    @DisplayName("Get all users")
    void whenGetAllUsers_thenReturnOk() throws Exception {
        UserResponse responseFromService = createUserResponse();
        when(userService.getAll()).thenReturn(List.of(responseFromService));

        mockMvc.perform(get(COMMON_USER_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(FIRST_USERNAME))
                .andExpect(jsonPath("$[0].email").value(FIRST_EMAIL))
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    private UserResponse createUserResponse() {
        OrderResponse orderResponse = OrderResponse.builder()
                .products(List.of("Product1, Product2"))
                .status("CONFIRMED")
                .cost("500")
                .build();
        return UserResponse.builder()
                .username(FIRST_USERNAME)
                .email(FIRST_EMAIL)
                .orders(List.of(orderResponse))
                .build();
    }

}

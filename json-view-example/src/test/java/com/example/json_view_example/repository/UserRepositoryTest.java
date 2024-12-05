package com.example.json_view_example.repository;

import com.example.json_view_example.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.json_view_example.TestConstant.FIRST_EMAIL;
import static com.example.json_view_example.TestConstant.FIRST_USERNAME;
import static com.example.json_view_example.TestConstant.SECOND_EMAIL;
import static com.example.json_view_example.TestConstant.SECOND_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save user")
    void saveUserTest() {
        User userForSave = User.builder()
                .email(FIRST_EMAIL)
                .username(FIRST_USERNAME)
                .build();

        User userFromDatabase = userRepository.save(userForSave);

        assertThat(userFromDatabase).isNotNull();
        assertThat(userFromDatabase.getId()).isPositive();
        assertThat(userFromDatabase.getEmail()).isEqualTo(FIRST_EMAIL);
    }

    @Test
    @DisplayName("Get user by id")
    void getUserByIdTest() {
        User userForSave = User.builder()
                .email(FIRST_EMAIL)
                .username(FIRST_USERNAME)
                .build();
        User savedUser = userRepository.save(userForSave);

        User user = userRepository.findById(savedUser.getId()).orElse(null);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(FIRST_EMAIL);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsersTest() {
        User user1ForSave = User.builder()
                .email(FIRST_EMAIL)
                .username(FIRST_USERNAME)
                .build();
        User user2ForSave = User.builder()
                .email(SECOND_EMAIL)
                .username(SECOND_USERNAME)
                .build();
        userRepository.saveAll(List.of(user1ForSave, user2ForSave));

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Delete user")
    void deleteUserTest() {
        User userForSave = User.builder()
                .email(FIRST_EMAIL)
                .username(FIRST_USERNAME)
                .build();
        User savedUser = userRepository.save(userForSave);

        userRepository.deleteById(savedUser.getId());
        User user = userRepository.findById(savedUser.getId()).orElse(null);

        assertThat(user).isNull();
    }

}

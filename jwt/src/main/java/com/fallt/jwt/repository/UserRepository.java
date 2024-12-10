package com.fallt.jwt.repository;

import com.fallt.jwt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name);

    boolean existsByUsername(String username);
}

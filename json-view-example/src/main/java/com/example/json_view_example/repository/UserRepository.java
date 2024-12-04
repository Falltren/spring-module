package com.example.json_view_example.repository;

import com.example.json_view_example.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

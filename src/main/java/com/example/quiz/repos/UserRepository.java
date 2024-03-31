package com.example.quiz.repos;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

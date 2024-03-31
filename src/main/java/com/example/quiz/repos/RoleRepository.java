package com.example.quiz.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}

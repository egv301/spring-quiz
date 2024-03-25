package com.example.quiz.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quiz.domain.Subject;
import com.example.quiz.domain.User;
import com.example.quiz.domain.UserAnswer;

public interface UserAnswerRepo extends JpaRepository<UserAnswer, Long> {
	List<UserAnswer> findByUserAndSubject(User user, Subject subject);
}
package com.example.quiz.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz.domain.Question;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {

    
}

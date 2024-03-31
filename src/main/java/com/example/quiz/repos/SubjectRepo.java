package com.example.quiz.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz.models.Subject;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {}

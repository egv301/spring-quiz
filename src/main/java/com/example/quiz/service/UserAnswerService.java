package com.example.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz.domain.Subject;
import com.example.quiz.domain.User;
import com.example.quiz.domain.UserAnswer;
import com.example.quiz.repos.UserAnswerRepo;

@Service
public class UserAnswerService {
	@Autowired
	UserAnswerRepo userAnswerRepo;
	
	public void saveAll(List<UserAnswer> userAnswerList) {
		userAnswerRepo.saveAll(userAnswerList);
	}

	public List<UserAnswer> getUserAnswers(User user, Subject subject) {
		return userAnswerRepo.findByUserAndSubject(user,subject);
	}
	
	public List<UserAnswer> findAll() {
		return userAnswerRepo.findAll();
	}
}

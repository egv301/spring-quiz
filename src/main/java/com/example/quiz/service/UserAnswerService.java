package com.example.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;
import com.example.quiz.models.User;
import com.example.quiz.models.UserAnswer;
import com.example.quiz.repos.UserAnswerRepo;

@Service
public class UserAnswerService {
	@Autowired
	UserAnswerRepo userAnswerRepo;
	
	public void save(UserAnswer userAnswer) {
		userAnswerRepo.save(userAnswer);
	}
	
	public void addQuizAnswer(User user, Subject subject, Question question, Answer answer) {
		UserAnswer userAnswer = userAnswerRepo
								.findByUserAndSubjectAndQuestion(user,subject,question)
								.orElse(new UserAnswer(user, subject, question));
		userAnswer.setAnswer(answer);
		userAnswerRepo.save(userAnswer);
	}
	
	public void saveAll(List<UserAnswer> userAnswerList) {
		userAnswerRepo.saveAll(userAnswerList);
	}

	public List<UserAnswer> getUserAnswers(User user, Subject subject) {
		return userAnswerRepo.findByUserAndSubject(user,subject);
	}
	
	public List<Long> getUserAnswersIds(User user, Subject subject) {
		return userAnswerRepo.getUserAnswersIds(user,subject);
	}
	
	public List<UserAnswer> findAll() {
		return userAnswerRepo.findAll();
	}
}

package com.example.quiz.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz.dto.QuizResultDTO;
import com.example.quiz.dto.UserQuizResultDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.models.QuizResult;
import com.example.quiz.models.Subject;
import com.example.quiz.models.User;
import com.example.quiz.projections.IQuizCount;
import com.example.quiz.repos.QuizResultRepository;

@Service
public class QuizResultService {
	@Autowired
	QuizResultRepository quizResultRepository;
	@Autowired
	SubjectService subjectService;
	@Autowired
	UserService userService;
	
	public void saveResults(User user, Subject subject, int result) {
		quizResultRepository.save(new QuizResult(user, subject, result));
	}
	
	public List<UserQuizResultDTO> getStatsBySubject(Long subject_id) throws NotFoundException{
		Subject subject = subjectService.getSubject(subject_id);
		List<UserQuizResultDTO> quizResult = 
				quizResultRepository
				.findBySubjectOrderByResultDesc(subject)
				.stream()
				.map(result->
					new UserQuizResultDTO(
							result.getUser().getId(),
							result.getUser().getUsername(),
							result.getResult()
				)).collect(Collectors.toList());
		
		return quizResult;
	}
	
	public List<IQuizCount> quizCount(){
		return quizResultRepository.countQuizes();
	}

	public List<QuizResultDTO> getStatsByUser(Long user_id) {
		User user = userService.findUserById(user_id);
		List<QuizResultDTO> quizResult = quizResultRepository
				.findByUserOrderByResultDesc(user)
				.stream()
				.map(result->
					new QuizResultDTO(
							result.getSubject().getId(),
							result.getSubject().getTitle(),
							result.getResult()
					)).collect(Collectors.toList());
		
		return quizResult;
	}

	
	public List<Long> getSubjectIDS(User user) {
		return quizResultRepository.getSubjectIDS(user);
	}

	public boolean existsByUserAndSubject(User user, Subject subject) {
		return quizResultRepository.existsByUserAndSubject(user,subject);
	}
}

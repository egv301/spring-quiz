package com.example.quiz.service;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.AnswersQuestionsDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;
import com.example.quiz.repos.AnswerRepo;

@Service
public class AnswerService {
	@Autowired
    private AnswerRepo answerRepo;

	@Autowired
	QuestionService questionService;

	public Answer getAnswer(Long answer_id) throws NotFoundException{
		return answerRepo.findById(answer_id).orElseThrow(()->new NotFoundException("Answer not found"));
	}

	public AnswerDTO getAnswerDTO(Long answer_id) throws NotFoundException{
		Answer answer = answerRepo.findById(answer_id).orElseThrow(()->new NotFoundException("Answer not found"));
		return new AnswerDTO(
			answer.getId(),
			answer.getTitle(),
			answer.getCorrect()
		);
	}

	public List<AnswerDTO> getAnswerListDTO(Long question_id) throws NotFoundException{
		Question question = questionService.getQuestion(question_id);
		return question.getAnswers()
			.stream()
			.map(answer->
				new AnswerDTO(answer.getId(),answer.getTitle(),answer.getCorrect()))
			.collect(Collectors.toList());
	}

	public AnswersQuestionsDTO getAnswerDetails(Long question_id) throws NotFoundException{
		Question question = questionService.getQuestion(question_id);
		List<AnswerDTO> answerList = question.getAnswers()
			.stream()
			.map(answer->
				new AnswerDTO(
					answer.getId(),
					answer.getTitle(),
					answer.getCorrect()))
			.collect(Collectors.toList());
		return new AnswersQuestionsDTO(
			question.getId(),
			question.getTitle(),
			answerList
		);
	}
	
	public List<Long> getAnswerIds(List<Question> questionList){
		 return answerRepo.getAnswerIds(questionList);
	}
	
	public void addAnswer(Long question_id, AnswerDTO answerDTO) throws NotFoundException {
		Question question = questionService.getQuestion(question_id);
		answerRepo.save(new Answer(
			answerDTO.getAnswerTitle(),
			question
		));
	}
	
//	public boolean checkAnswerQuestionSubject(Long answerId, Long questionId, Long subjectId) {
//		return answerRepo.checkAnswerQuestionSubject(answerId, questionId, subjectId);
//	}

	public void updateAnswer(Long answer_id, AnswerDTO answerDTO) throws NotFoundException {
		Answer answer = answerRepo.findById(answer_id).orElseThrow(()->new NotFoundException("Answer not found"));
		answer.setTitle(answerDTO.getAnswerTitle());
		answerRepo.save(answer);
	}

	public void removeAnswer(Long answer_id) {
		answerRepo.deleteById(answer_id);
	}

	public void setCorrectAnswer(Long answer_id) throws NotFoundException {
		Answer answer = getAnswer(answer_id);
		answer.setCorrect(true);
		answerRepo.save(answer);
		setOtherAnwersToExcept(answer_id);
	}
	
	public void setOtherAnwersToExcept(Long answerId) {
		answerRepo.setOtherAnwersToExcept(answerId);
	}

	public List<Answer> getCorrectAnswersBySubject(Subject subject) {
		return answerRepo.getCorrectAnswersBySubject(subject);
	}
	
	public List<Answer> getAnswersBySubject(Subject subject) {
		return answerRepo.getAnswersBySubject(subject);
	}
	

}

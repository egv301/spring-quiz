package com.example.quiz.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.Subject;
import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.AnswersQuestionsDTO;
import com.example.quiz.exceptions.NotFoundException;
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
			answer.getIsCorrect()
		);
	}

	public List<AnswerDTO> getAnswerListDTO(Long question_id) throws NotFoundException{
		Question question = questionService.getQuestion(question_id);
		return question.getAnswers()
			.stream()
			.map(answer->
				new AnswerDTO(answer.getId(),answer.getTitle(),answer.getIsCorrect()))
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
					answer.getIsCorrect()))
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
			answerDTO.isCorrect(),
			question
		));
	}

	public void updateAnswer(Long answer_id, AnswerDTO answerDTO) throws NotFoundException {
		Answer answer = answerRepo.findById(answer_id).orElseThrow(()->new NotFoundException("Answer not found"));
		answer.setTitle(answerDTO.getAnswerTitle());
		answer.setIsCorrect(answerDTO.isCorrect());
		answerRepo.save(answer);
	}

	public void removeAnswer(Long answer_id) {
		answerRepo.deleteById(answer_id);
	}
}

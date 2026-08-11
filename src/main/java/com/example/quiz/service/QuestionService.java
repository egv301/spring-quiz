package com.example.quiz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.dto.QuestionsSubjectDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;
import com.example.quiz.repos.QuestionRepo;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;
	private final SubjectService subjectService;

	public QuestionService(QuestionRepo questionRepo, SubjectService subjectService) {
		this.questionRepo = questionRepo;
		this.subjectService = subjectService;
	}

	public Question getQuestion(Long question_id) throws NotFoundException{
		return questionRepo.findById(question_id).orElseThrow(()->new NotFoundException("Question not found"));
	}

	public QuestionDTO getQuestionDTO(Long question_id) throws NotFoundException{
		Question question = getQuestion(question_id);
		return new QuestionDTO(
			question.getId(),
			question.getTitle(),
			question.getPoints(),
			question.getSubject().getId()
		);
	}

	public QuestionsSubjectDTO getQuestionDetails(Long subject_id) throws NotFoundException{
		Subject subject = subjectService.getSubject(subject_id);
		List<QuestionDTO> questionList = subject.getQuestions()
			.stream()
			.map(question->
				new QuestionDTO(
					question.getId(),
					question.getTitle(),
					question.getPoints()))
			.collect(Collectors.toList());
		
		return new QuestionsSubjectDTO(
			subject.getId(),
			subject.getTitle(),
			questionList
		);
	}

	public QuestionDTO addQuestion(Long subject_id, QuestionDTO questionDto) throws NotFoundException {
		Subject subject = subjectService.getSubject(subject_id);
		Question question = questionRepo.save(new Question(
			questionDto.getTitle(), 
			questionDto.getPoints(), 
			subject));
		return new QuestionDTO(
			question.getId(),
			question.getTitle(),
			question.getPoints(),
			question.getSubject().getId()
		);
	}
	
	public void updateQuestion(Long question_id, QuestionDTO questionDto) throws NotFoundException {
		Question question = questionRepo.findById(question_id).orElseThrow(()->new NotFoundException("Question not found"));
		question.setTitle(questionDto.getTitle());
		question.setPoints(questionDto.getPoints());
		questionRepo.save(question);
	}
	
	public void removeQuestion(Long id) {
		questionRepo.deleteById(id);
	}
}

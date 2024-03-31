package com.example.quiz.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.AnswerWithStatusDTO;
import com.example.quiz.dto.QuestionAnswersDTO;
import com.example.quiz.dto.QuestionAnswersResultsDTO;
import com.example.quiz.dto.QuizAnswerDTO;
import com.example.quiz.dto.QuizDTO;
import com.example.quiz.dto.QuizDetailedResulstDTO;
import com.example.quiz.dto.QuizResultDTO;
import com.example.quiz.dto.SubjectDTO;
import com.example.quiz.dto.SubjectListDTO;
import com.example.quiz.enums.AnswerStatus;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.exceptions.QuizAlreadyPassedException;
import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.QuizResult;
import com.example.quiz.models.Subject;
import com.example.quiz.models.User;
import com.example.quiz.models.UserAnswer;
import com.example.quiz.repos.QuizResultRepository;
import com.example.quiz.repos.SubjectRepo;

import javassist.expr.NewArray;

@Service
public class QuizService {
	@Autowired
	SubjectRepo subjectRepo;
	@Autowired
    QuizResultService quizResultService;
	@Autowired
    UserService userService;
	@Autowired
	SubjectService subjectService;
	@Autowired
	AnswerService answerService;
	@Autowired
	QuestionService questionService;
	@Autowired
	UserAnswerService userAnswerService;
	
	public QuizResultDTO processResults(Principal authUser,Long subjectId) throws NotFoundException {
		int score = 0;
		User user = userService.findByUsername(authUser.getName());
		Subject subject = subjectService.getSubject(subjectId);
		
		List<Answer> answerList = answerService.getCorrectAnswersBySubject(subject);
		List<UserAnswer> userAnswers = userAnswerService.getUserAnswers(user, subject);
		System.out.println(userAnswers);
		for(UserAnswer userAnswerItem : userAnswers) {
			if(answerList.contains(userAnswerItem.getAnswer())) {
				score += userAnswerItem.getQuestion().getPoints();
			}
		}
		quizResultService.saveResults(user, subject, score);
		return new QuizResultDTO(subject.getId(),subject.getTitle(),score);
	}
	
	public List<SubjectListDTO> getSubjectList(Principal auth){
		User user = null;
		List<Long> subjectIds = new ArrayList<>();
		List<SubjectListDTO> subjectListDto = new ArrayList<>();
		List<Subject> subjectList = subjectService.subjectList();
		if(auth!=null) {
			user = userService.findByUsername(auth.getName());
			subjectIds = quizResultService.getSubjectIDS(user);
		}
		for(Subject subject : subjectList) {
			boolean canPass = subjectIds.contains(subject.getId()) ? false : true;
			subjectListDto.add(new SubjectListDTO(subject.getId(), subject.getTitle(), canPass));
		}
		return subjectListDto;
			
	}
	
	public void checkQuizPassed(Principal auth,Long subjectId) throws NotFoundException, QuizAlreadyPassedException {
		User user = userService.findByUsername(auth.getName());
		Subject subject = subjectService.getSubject(subjectId);
		if(quizResultService.existsByUserAndSubject(user, subject)){
			throw new QuizAlreadyPassedException("Quiz already passed");
		}
	}

	public QuizDetailedResulstDTO showDetailedUserAnswers(Principal authUser,Long subjectId) throws NotFoundException {
		Subject subject = subjectService.getSubject(subjectId);
		User user = userService.findByUsername(authUser.getName());
		
		List<Answer> answerList = answerService.getAnswersBySubject(subject);
		List<Long> userAnswerIdsList = userAnswerService.getUserAnswersIds(user, subject);
		
		List<QuestionAnswersResultsDTO> questionAnswersResultsListDTO = new ArrayList<>();
		List<AnswerWithStatusDTO> answerWithStatusListDTO = new ArrayList<>();
		Map<Long, QuestionAnswersResultsDTO> questionAnswersMap = new HashMap<Long, QuestionAnswersResultsDTO>();
		
		for(Answer answer : answerList) {
			AnswerWithStatusDTO answerWithStatusDTO = new AnswerWithStatusDTO();
			answerWithStatusDTO.setAnswerId(answer.getId());
			answerWithStatusDTO.setAnswerTitle(answer.getTitle());
			if(answer.getCorrect()) {
				answerWithStatusDTO.setAnswerStatus(AnswerStatus.CORRECT);
			} else if(userAnswerIdsList.contains(answer.getId())) {
				answerWithStatusDTO.setAnswerStatus(AnswerStatus.INCORRECT);
			} else {
				answerWithStatusDTO.setAnswerStatus(AnswerStatus.NOTCHOSEN);
			}
			
			if(questionAnswersMap.containsKey(answer.getQuestion().getId())) {
				questionAnswersMap.get(answer.getQuestion().getId()).getAnswers().add(answerWithStatusDTO);
			} 
			else {
				List<AnswerWithStatusDTO> answerWithStatusDto = new ArrayList<>();
				answerWithStatusDto.add(answerWithStatusDTO);
				questionAnswersMap.put(answer.getQuestion().getId(),
						new QuestionAnswersResultsDTO(
								answer.getQuestion().getId(),
								answer.getQuestion().getTitle(),
								answerWithStatusDto)
				);
			}
		}
		return new QuizDetailedResulstDTO(
				subject.getId(),
				subject.getTitle(),
				questionAnswersMap.values().stream().collect(Collectors.toList())
		);
		
	}
	
	private SubjectDTO mapToSubjectDTO(Subject subject) {
        return new SubjectDTO(subject.getId(), subject.getTitle());
    }
    
    public QuizDTO getQuizDetails(Principal user,Long subjectId) throws NotFoundException, QuizAlreadyPassedException {
		Subject subject = subjectService.getSubject(subjectId);
		checkQuizPassed(user,subjectId);
        List<QuestionAnswersDTO> questions = subject.getQuestions().stream()
                .map(this::mapToQuestionDTO)
                .collect(Collectors.toList());
        return new QuizDTO(subject.getId(),subject.getTitle(), questions);
    }

    private QuestionAnswersDTO mapToQuestionDTO(Question question) {
        List<AnswerDTO> answers = question.getAnswers().stream()
                .map(this::mapToAnswerDTO)
                .collect(Collectors.toList());

        return new QuestionAnswersDTO(question.getId(), question.getTitle(), answers);
    }

    private AnswerDTO mapToAnswerDTO(Answer answer) {
        return new AnswerDTO(answer.getId(), answer.getTitle(), answer.getCorrect());
    }

	public void addQuizAsnwer(Principal auth,QuizAnswerDTO quizAnswerDto) throws NotFoundException {
		User user = userService.findByUsername(auth.getName());
		Subject subject = subjectService.getSubject(quizAnswerDto.getSubjectId());
		Question question = questionService.getQuestion(quizAnswerDto.getQuestionId());
		Answer answer = answerService.getAnswer(quizAnswerDto.getAnswerId());
		if(subject.getQuestions().contains(question) && question.getAnswers().contains(answer)) {
			userAnswerService.addQuizAnswer(user, subject, question, answer);
		}
	}
}

package com.example.quiz.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.Question;
import com.example.quiz.domain.QuizResult;
import com.example.quiz.domain.Subject;
import com.example.quiz.domain.User;
import com.example.quiz.domain.UserAnswer;
import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.AnswerWithStatusDTO;
import com.example.quiz.dto.QuestionAnswersDTO;
import com.example.quiz.dto.QuestionAnswersResultsDTO;
import com.example.quiz.dto.QuizDTO;
import com.example.quiz.dto.QuizDetailedResulstDTO;
import com.example.quiz.dto.QuizResultDTO;
import com.example.quiz.dto.SubjectDTO;
import com.example.quiz.dto.SubjectListDTO;
import com.example.quiz.enums.AnswerStatus;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.exceptions.QuizAlreadyPassedException;
import com.example.quiz.repos.QuizResultRepository;
import com.example.quiz.repos.SubjectRepo;

@Service
public class QuizService {
	@Autowired
	SubjectRepo subjectRepo;
	@Autowired
    QuizResultRepository quizResultRepo;
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
	
	
	
	public QuizResultDTO processResults(Principal authUser,Long subject_id,MultiValueMap<String, String> answers) throws NotFoundException {
		int score=0;
		User user = userService.findByUsername(authUser.getName());
		Subject subject = subjectService.getSubject(subject_id);
		Map<Long, Integer> questionsPoints = new HashMap<Long, Integer>();
		Map<Long, List<Long>> questionsCorrectAnswers = new HashMap<Long, List<Long>>();
		
		for(Question question : subject.getQuestions()) {
			questionsPoints.put(question.getId(), question.getPoints());
			List<Long> correctAnswerIds = new ArrayList<Long>();
			for(Answer answer : question.getAnswers()) {
				if(answer.getIsCorrect()==true) {
					correctAnswerIds.add(answer.getId());
				}
			}
			questionsCorrectAnswers.put(question.getId(), correctAnswerIds);
		}
		
		for (Entry<String, List<String>>  entry : answers.entrySet()) {
			Long questionId = (long) Integer.parseInt(entry.getKey());
			List<Long> answerIds = new ArrayList<>();
			for(String s : entry.getValue()) {
				answerIds.add((long) Integer.parseInt(s));
			}
			
			if(questionsCorrectAnswers.containsKey(questionId)) {
				if(answerIds.size() == questionsCorrectAnswers.get(questionId).size() && answerIds.containsAll(questionsCorrectAnswers.get(questionId))) {
	        		score += questionsPoints.get(questionId);
	        	}
	        }
		}
		quizResultRepo.save(new QuizResult(user,subject,score));
		saveResults(subject,user,answers);
		return new QuizResultDTO(subject.getId(), subject.getTitle(), score);
	}
	
	public void saveResults(Subject subject, User user, MultiValueMap<String, String> answers) throws NotFoundException {
		List<UserAnswer> userAswers = new ArrayList<>();
		List<Long> answerIds = answerService.getAnswerIds(subject.getQuestions());
		
		for (Entry<String, List<String>>  entry : answers.entrySet()) {
			Long questionId = (long) Integer.parseInt(entry.getKey());
			Question question = questionService.getQuestion(questionId);
			List<Long> answersIds = new ArrayList<>();
			for(String s : entry.getValue()) {
				if(answerIds.contains((long) Integer.parseInt(s))) {
					userAswers.add(new UserAnswer(
							user,
							subject,
							question, 
							answerService.getAnswer((long) Integer.parseInt(s))));
				}
			}
		}
		userAnswerService.saveAll(userAswers);
	}
	
	public List<SubjectListDTO> getSubjectList(Principal auth){
		User user = null;
		List<Long> subjectIds = new ArrayList<>();
		List<SubjectListDTO> subjectListDto = new ArrayList<>();
		List<Subject> subjectList = subjectService.subjectList();
		if(auth!=null) {
			user = userService.findByUsername(auth.getName());
			subjectIds = quizResultRepo.getSubjectIDS(user);
		}
		for(Subject subject : subjectList) {
			boolean canPass = subjectIds.contains(subject.getId()) ? false : true;
			subjectListDto.add(new SubjectListDTO(subject.getId(), subject.getTitle(), canPass));
		}
		return subjectListDto;
			
	}
	
	public boolean checkQuizPassed(Principal auth,Long subjectId) throws NotFoundException, QuizAlreadyPassedException {
		User user = userService.findByUsername(auth.getName());
		Subject subject = subjectService.getSubject(subjectId);
		if(quizResultRepo.existsByUserAndSubject(user, subject)){
			return true;
		}
		return false;
	}

	public QuizDetailedResulstDTO showUserAnswers(Long subject_id, Principal authUser) throws NotFoundException {
		Subject subject = subjectService.getSubject(subject_id);
		User user = userService.findByUsername(authUser.getName());
		Map<Long, List<Long>> userAnswersMap = new HashMap<>();
		List<UserAnswer> userAnswersList = userAnswerService.getUserAnswers(user,subject);
		//List<UserAnswer> userAnswersList = userAnswerService.findAll();
		for(UserAnswer userAnswer : userAnswersList) {
			if(userAnswersMap.containsKey(userAnswer.getQuestion().getId())) {
				List<Long> ansList = userAnswersMap.get(userAnswer.getQuestion().getId());
				ansList.add(userAnswer.getAnswer().getId());
				userAnswersMap.put(userAnswer.getQuestion().getId(), ansList);
			}else {
				List<Long> ansList = new ArrayList<>();
				ansList.add(userAnswer.getAnswer().getId());
				userAnswersMap.put(userAnswer.getQuestion().getId(), ansList);
			}
		} 
		//System.out.println(userAnswersMap);
		
		List<QuestionAnswersResultsDTO> questionAnswerList = new ArrayList<>();
		for(Question questionItem : subject.getQuestions()) {
			List<AnswerWithStatusDTO> answerList = new ArrayList<>();
			
			for(Answer answerItem : questionItem.getAnswers()) {
				if(answerItem.getIsCorrect()) {
					answerList.add(new AnswerWithStatusDTO(answerItem.getId(),answerItem.getTitle(),AnswerStatus.CORRECT));
				} else if(userAnswersMap.containsKey(questionItem.getId()) 
						&& userAnswersMap.get(questionItem.getId()).contains(answerItem.getId())){
						answerList.add(new AnswerWithStatusDTO(answerItem.getId(),answerItem.getTitle(),AnswerStatus.INCORRECT));
				} else {
					answerList.add(new AnswerWithStatusDTO(answerItem.getId(),answerItem.getTitle(),AnswerStatus.NOTCHOSEN));
				}
			}
			questionAnswerList.add(new QuestionAnswersResultsDTO(questionItem.getId(),questionItem.getTitle(),answerList));
		}
		return new QuizDetailedResulstDTO(subject.getId(),subject.getTitle(),questionAnswerList);
		
	}
	
    private SubjectDTO mapToSubjectDTO(Subject subject) {
        return new SubjectDTO(subject.getId(), subject.getTitle());
    }
    
    public QuizDTO getQuizDetails(Principal user,Long subjectId) throws NotFoundException, QuizAlreadyPassedException {
		Subject subject = subjectService.getSubject(subjectId);
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
        return new AnswerDTO(answer.getId(), answer.getTitle(), answer.getIsCorrect());
    }
	

	
}

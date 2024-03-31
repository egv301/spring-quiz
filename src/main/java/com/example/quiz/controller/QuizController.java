package com.example.quiz.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.quiz.dto.QuizAnswerDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.exceptions.QuizAlreadyPassedException;
import com.example.quiz.service.QuizResultService;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.SubjectService;
import com.example.quiz.service.UserService;;

@Controller
public class QuizController {
	
	@Autowired
	QuizService quizService;
	@Autowired
	UserService userService;
	@Autowired
	QuizResultService quizResultService;
	@Autowired
	SubjectService subjectService;
	
	@GetMapping("/")
	public String quizList(Principal user, Model model) {
		model.addAttribute("loggedIn",user!=null);
		model.addAttribute("isAdmin",userService.isAdmin(user));
		model.addAttribute("subjectsList",quizService.getSubjectList(user));
		return "quiz/quiz-list";
	}
	
	@GetMapping("/quiz-start/{subject_id}")
	public String startQuiz(@PathVariable("subject_id") Long subject_id, Principal user,Model model) throws NotFoundException, QuizAlreadyPassedException {
		model.addAttribute("quizDetail",quizService.getQuizDetails(user,subject_id));
		return "quiz/quiz";
	}
	
	@GetMapping("/show-results/{subject_id}")
	public String submitQuiz(@PathVariable("subject_id") Long subject_id,Principal authUser,Model model) throws NotFoundException {
		model.addAttribute("quizResult",quizService.processResults(authUser,subject_id));
		return "quiz/result";
	}
	
	@PostMapping("/submit-quiz-answer")
	public ResponseEntity<String> addQuizAnswer(@Valid QuizAnswerDTO quizAnswerDto,Principal auth) throws NotFoundException {
		quizService.addQuizAsnwer(auth,quizAnswerDto);
		return ResponseEntity.ok("OK");
	}
	
	@GetMapping("/show-quiz-answers/{subject_id}")
	public String showQuizAnswers(@PathVariable("subject_id") Long subjectId, Principal authUser,Model model) throws NotFoundException {
		model.addAttribute("quizDetail",quizService.showDetailedUserAnswers(authUser,subjectId));
		return "quiz/quiz-results-detail";
	}
}

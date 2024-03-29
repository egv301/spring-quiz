package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quiz.domain.*;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.exceptions.QuizAlreadyPassedException;
import com.example.quiz.service.AnswerService;
import com.example.quiz.service.QuizResultService;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.SubjectService;
import com.example.quiz.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;;

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
	
	@PostMapping("/submit-quiz/{subject_id}")
	public String submitQuiz(@PathVariable("subject_id") Long subject_id, @RequestParam MultiValueMap<String, String> answers,Principal authUser,Model model) throws NotFoundException {
		model.addAttribute("quizResult",quizService.processResults(authUser,subject_id,answers));
		return "quiz/result";
	}
	
	@GetMapping("/show-quiz-answers/{subject_id}")
	public String showQuizAnswers(@PathVariable("subject_id") Long subject_id, Principal authUser,Model model) throws NotFoundException {
		model.addAttribute("quizDetail",quizService.showUserAnswers(subject_id, authUser));
		//quizService.showUserAnswers(subject_id, authUser);
		return "quiz/quiz-results-detail";
	}
}

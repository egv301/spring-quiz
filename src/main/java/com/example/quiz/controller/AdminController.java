package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.service.QuizResultService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	QuizResultService quizResultService;
	
	@GetMapping("/stats")
    public String showStats(Model model) {
		model.addAttribute("quizCount",quizResultService.quizCount());
    	return "stats/stats";
    }
	
	@GetMapping("/stats/{subject_id}")
    public String showStatsBySubject(@PathVariable("subject_id") Long subject_id,Model model) throws NotFoundException {
		model.addAttribute("quizStats",quizResultService.getStatsBySubject(subject_id));
		return "stats/statsBySubject";
	}
	
	@GetMapping("/stats/user/{user_id}")
	public String showStatsByUser(@PathVariable("user_id") Long user_id,Model model) throws NotFoundException {
		model.addAttribute("quizStats",quizResultService.getStatsByUser(user_id));
		return "stats/statsByUser";
	}
}

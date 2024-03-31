package com.example.quiz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.quiz.models.User;
import com.example.quiz.service.UserService;

@Controller
public class ProfileController {
	@Autowired
	UserService userService;
	
	@GetMapping("/profile")
	public String profile(Principal authUser,Model model) {
		User user = userService.findByUsername(authUser.getName());
		model.addAttribute("user",user);
		System.out.println(user.getQuizResults());
		return "profile/profile";
	}
	
	
}

package com.example.quiz.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.quiz.dto.RegistrationDTO;
import com.example.quiz.service.UserService;
import com.example.quiz.util.ControllerUtils;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
    	return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid RegistrationDTO userForm, BindingResult bindingResult, Model model) {
    	if(bindingResult.hasErrors()) {
    		Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
    		model.mergeAttributes(errorsMap);
    		return "registration";
    	}
    	if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordDontMatch", "Password do no match");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameExists", "User with this username already exists");
            return "registration";
        }
        return "redirect:/";
    }
}
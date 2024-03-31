package com.example.quiz.controller;
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.service.QuestionService;
import com.example.quiz.service.SubjectService;
import com.example.quiz.util.ControllerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class QuestionController {
    
	@Autowired
    private QuestionService questionService;
    @Autowired
    SubjectService subjectService;
    
    @GetMapping("/question-list/{subject_id}")
    public String showAllQuestions(@PathVariable("subject_id") Long subject_id, Model model) throws NotFoundException {
        model.addAttribute("question_details",questionService.getQuestionDetails(subject_id));
    	return "question/question-list";
    }
    
    @GetMapping("/show-question/{question_id}")
    public String showQuestion(@PathVariable("question_id") Long question_id, Model model) throws NotFoundException {
		model.addAttribute("question",questionService.getQuestionDTO(question_id));
    	return "question/show-question";
    }
    
    @GetMapping("/add-question/{subject_id}")
    public String showMessageForm(@PathVariable("subject_id") Long subject_id,Model model) throws NotFoundException {
    	model.addAttribute("subject",subjectService.getSubjectDTO(subject_id));
        return "question/add-question";
    }
    
    @PostMapping("/add-question/{subject_id}")
    public String addQuestion(@PathVariable("subject_id") Long subject_id,@Valid QuestionDTO questionDto, BindingResult bindingResult, Model model) throws NotFoundException {
		if (bindingResult.hasErrors()) {
    		Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
    		model.addAttribute(subject_id);
        	model.mergeAttributes(errorsMap);
        	return "question/add-question";
    	}
    	questionService.addQuestion(subject_id, questionDto);
       	return "redirect:/admin/question-list/" + subject_id;
    }
    
    @GetMapping("/edit-question/{question_id}")
    public String editQuestionForm(@PathVariable("question_id") Long question_id, Model model) throws NotFoundException{
       	model.addAttribute("question", questionService.getQuestionDTO(question_id));
        return "question/edit-question";
        
    }
    
    @PostMapping("/edit-question/{question_id}")
    public String updateQuestion(@PathVariable("question_id") Long question_id,@Valid QuestionDTO questionDto,BindingResult bindingResult,Model model) throws NotFoundException{
    	if (bindingResult.hasErrors()) {
        	Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
        	model.mergeAttributes(errorsMap);
        	return "question/edit-question";
        }
    	questionService.updateQuestion(question_id, questionDto);
        return "redirect:/admin/show-question/"+question_id;
    	
    }
    
    @PostMapping("/remove-question/{question_id}")
    public String removeQuestion(@PathVariable("question_id") Long question_id, Model model) throws NotFoundException{
        Long redirectId = questionService.getQuestion(question_id).getSubject().getId();
    	questionService.removeQuestion(question_id);
        return "redirect:/admin/show-subject/"+redirectId;
    }
}
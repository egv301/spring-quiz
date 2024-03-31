package com.example.quiz.controller;

import com.example.quiz.dto.SubjectDTO;
import com.example.quiz.exceptions.NotFoundException;
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
public class SubjectController {
    
	@Autowired
    private SubjectService subjectService;
    
    @GetMapping("/subject-list")
    public String greeting(Model model) {
    	model.addAttribute("subjects",subjectService.subjectList());
        return "subject/subject-list";
    }
    
    @GetMapping("/show-subject/{subject_id}")
    public String showAnswer(@PathVariable("subject_id") Long subject_id, Model model) throws NotFoundException {
        model.addAttribute("subject",subjectService.getSubjectWithQuestions(subject_id));
        return "subject/show-subject";
    }
    
    @GetMapping("/add-subject")
    public String showSubjectForm() {
    	return "subject/add-subject";
    }
    
    @PostMapping("/add-subject")
    public String addSubject(@Valid SubjectDTO subjectDto,BindingResult bindingResult,Model model) throws NotFoundException {
    	if (bindingResult.hasErrors()) {
    		Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
    		model.mergeAttributes(errorsMap);
    		return "subject/add-subject";
    	}
    	subjectService.saveSubject(subjectDto);
    	return "redirect:/admin/subject-list";
    }
    
    @GetMapping("/edit-subject/{subject_id}")
    public String editMessageForm(@PathVariable("subject_id") Long subject_id, Model model) throws NotFoundException{
        model.addAttribute("subject", subjectService.getSubjectDTO(subject_id));
        return "subject/edit-subject";
    }

    @PostMapping("/edit-subject/{subject_id}")
    public String editSubject(@PathVariable("subject_id") Long subject_id,@Valid SubjectDTO subjectDto,BindingResult bindingResult,Model model) throws NotFoundException{
    	if (bindingResult.hasErrors()) {
    		Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
    		model.mergeAttributes(errorsMap);
    		return "subject/add-subject";
    	}
    	subjectService.updateSubject(subject_id,subjectDto);
    	return "redirect:/admin/subject-list";
    }
    
    @PostMapping("/remove-subject/{subject_id}")
    public String removeSubject(@PathVariable("subject_id") Long subject_id){
    	subjectService.removeSubject(subject_id);
    	return "redirect:/admin/subject-list";
    }
}
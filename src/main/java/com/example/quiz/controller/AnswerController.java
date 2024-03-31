package com.example.quiz.controller;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;
import com.example.quiz.service.AnswerService;
import com.example.quiz.util.ControllerUtils;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AnswerController {
    
	@Autowired
    private AnswerService answerService;
    
    @GetMapping("/answer-list/{question_id}")
    public String greeting(@PathVariable("question_id") Long question_id, Model model) throws NotFoundException {
    	model.addAttribute("answersDetails",answerService.getAnswerDetails(question_id));
    	return "answer/answer-list";
    }
    
    @GetMapping("/add-answer/{question_id}")
    public String showAnswerForm(@PathVariable("question_id") Long question_id,Model model) {
    	model.addAttribute("question_id",question_id);
    	return "answer/add-answer";
    }
    
    @PostMapping("/add-answer/{question_id}")
    public String addAnswer(@PathVariable("question_id") Long question_id, @Valid AnswerDTO answerDTO,BindingResult bindingResult, Model model) throws NotFoundException {
    	if (bindingResult.hasErrors()) {
			Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
			model.addAttribute(question_id);
			model.mergeAttributes(errorsMap);
			return "answer/add-answer";
    	}
    	System.out.println(answerDTO);
    	answerService.addAnswer(question_id, answerDTO);
    	return "redirect:/admin/answer-list/" + question_id;
    }
    
    @GetMapping("/edit-answer/{answer_id}")
    public String editAnswerForm(@PathVariable("answer_id") Long answer_id, Model model) throws NotFoundException{
       model.addAttribute("answer", answerService.getAnswerDTO(answer_id));
    	return "answer/edit-answer";
   }

    @PostMapping("/edit-answer/{answer_id}")
    public String updateAnswer(@PathVariable("answer_id") Long answer_id,@Valid AnswerDTO answerDto,BindingResult bindingResult, Model model) throws NotFoundException{
    	if (bindingResult.hasErrors()) {
			Map<String,String> errorsMap = ControllerUtils.getErrors(bindingResult);
			model.addAttribute("answer",answer_id);
			model.mergeAttributes(errorsMap);
			return "answer/edit-answer";
    	}
    	answerService.updateAnswer(answer_id,answerDto);
    	return "redirect:/admin/answer-list/"+ answerService.getAnswer(answer_id).getQuestion().getId();
	}
    
	@PostMapping("/set-correct-answer/{answer_id}")
	public String setCorrectAnswer(@PathVariable("answer_id") Long answer_id) throws NotFoundException{
		answerService.setCorrectAnswer(answer_id);
		return "redirect:/admin/answer-list/"+ answerService.getAnswer(answer_id).getQuestion().getId();
	}

    @GetMapping("/show-answer/{id}")
    public String showAnswer(@PathVariable("id") Long answer_id,Model model) throws NotFoundException{
    	model.addAttribute("answer",answerService.getAnswer(answer_id));
    	return "answer/show-answer";
    }
    
    @PostMapping("/remove-answer/{answer_id}")
    public String removeAnswer(@PathVariable("answer_id") Long answer_id,Model model) throws NotFoundException{
		Long redirectId = answerService.getAnswer(answer_id).getQuestion().getId();
    	answerService.removeAnswer(answer_id);
    	return "redirect:/admin/answer-list/"+ redirectId;
   }
}

package com.example.quiz.controller.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.quiz.config.ApiController;
import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.AnswersQuestionsDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.service.AnswerService;

@ApiController
@RequestMapping("/api")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/questions/{questionId}/answers")
    public AnswersQuestionsDTO list(@PathVariable Long questionId) throws NotFoundException {
        return answerService.getAnswerDetails(questionId);
    }

    @PostMapping("/questions/{questionId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long questionId, @Valid @RequestBody AnswerDTO answer) throws NotFoundException {
        answerService.addAnswer(questionId, answer);
    }

    @PutMapping("/answers/{answerId}")
    public void update(@PathVariable Long answerId, @Valid @RequestBody AnswerDTO answer) throws NotFoundException {
        answerService.updateAnswer(answerId, answer);
    }

    @PutMapping("/answers/{answerId}/correct")
    public void setCorrect(@PathVariable Long answerId) throws NotFoundException {
        answerService.setCorrectAnswer(answerId);
    }

    @DeleteMapping("/answers/{answerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long answerId) throws NotFoundException {
        answerService.removeAnswer(answerId);
    }
}

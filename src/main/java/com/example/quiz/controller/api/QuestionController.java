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
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.dto.QuestionsSubjectDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.service.QuestionService;

@ApiController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/subjects/{subjectId}/questions")
    public QuestionsSubjectDTO list(@PathVariable Long subjectId) throws NotFoundException {
        return questionService.getQuestionDetails(subjectId);
    }

    @PostMapping("/subjects/{subjectId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO create(@PathVariable Long subjectId, @Valid @RequestBody QuestionDTO question) throws NotFoundException {
        return questionService.addQuestion(subjectId, question);
    }

    @GetMapping("/questions/{questionId}")
    public QuestionDTO show(@PathVariable Long questionId) throws NotFoundException {
        return questionService.getQuestionDTO(questionId);
    }

    @PutMapping("/questions/{questionId}")
    public void update(@PathVariable Long questionId, @Valid @RequestBody QuestionDTO question) throws NotFoundException {
        questionService.updateQuestion(questionId, question);
    }

    @DeleteMapping("/questions/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long questionId) {
        questionService.removeQuestion(questionId);
    }
}

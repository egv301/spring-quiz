package com.example.quiz.controller.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.config.ApiController;
import com.example.quiz.dto.QuizAnswerDTO;
import com.example.quiz.dto.QuizDTO;
import com.example.quiz.dto.QuizDetailedResultsDTO;
import com.example.quiz.dto.QuizResultDTO;
import com.example.quiz.dto.SubjectListDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.exceptions.QuizAlreadyPassedException;
import com.example.quiz.service.QuizService;

@ApiController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<SubjectListDTO> list(Principal user) {
        return quizService.getSubjectList(user);
    }

    @GetMapping("/{subjectId}")
    public QuizDTO start(@PathVariable Long subjectId, Principal user)
            throws NotFoundException, QuizAlreadyPassedException {
        return quizService.getQuizDetails(user, subjectId);
    }

    @PostMapping("/answers")
    public void answer(@Valid @RequestBody QuizAnswerDTO answer, Principal user) throws NotFoundException {
        quizService.addQuizAnswer(user, answer);
    }

    @PostMapping("/{subjectId}/results")
    public QuizResultDTO finish(@PathVariable Long subjectId, Principal user)
            throws NotFoundException, QuizAlreadyPassedException {
        return quizService.processResults(user, subjectId);
    }

    @GetMapping("/{subjectId}/answers")
    public QuizDetailedResultsDTO answers(@PathVariable Long subjectId, Principal user) throws NotFoundException {
        return quizService.showDetailedUserAnswers(user, subjectId);
    }
}

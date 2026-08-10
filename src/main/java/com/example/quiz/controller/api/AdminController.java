package com.example.quiz.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quiz.config.ApiController;
import com.example.quiz.dto.QuizResultDTO;
import com.example.quiz.dto.UserQuizResultDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.projections.IQuizCount;
import com.example.quiz.service.QuizResultService;

@ApiController
@RequestMapping("/api/admin")
public class AdminController {
    private final QuizResultService quizResultService;

    public AdminController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @GetMapping("/stats")
    public List<IQuizCount> stats() {
        return quizResultService.quizCount();
    }

    @GetMapping("/stats/subjects/{subjectId}")
    public List<UserQuizResultDTO> statsBySubject(@PathVariable Long subjectId) throws NotFoundException {
        return quizResultService.getStatsBySubject(subjectId);
    }

    @GetMapping("/stats/users/{userId}")
    public List<QuizResultDTO> statsByUser(@PathVariable Long userId) {
        return quizResultService.getStatsByUser(userId);
    }
}

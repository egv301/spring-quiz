package com.example.quiz.controller.api;

import java.util.List;

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
import com.example.quiz.dto.SubjectDTO;
import com.example.quiz.dto.SubjectWithQuestionsDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.service.SubjectService;

@ApiController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectDTO> list() {
        return subjectService.subjectListDTO();
    }

    @GetMapping("/{subjectId}")
    public SubjectWithQuestionsDTO show(@PathVariable Long subjectId) throws NotFoundException {
        return subjectService.getSubjectWithQuestions(subjectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody SubjectDTO subject) {
        subjectService.saveSubject(subject);
    }

    @PutMapping("/{subjectId}")
    public void update(@PathVariable Long subjectId, @Valid @RequestBody SubjectDTO subject) throws NotFoundException {
        subjectService.updateSubject(subjectId, subject);
    }

    @DeleteMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long subjectId) {
        subjectService.removeSubject(subjectId);
    }
}

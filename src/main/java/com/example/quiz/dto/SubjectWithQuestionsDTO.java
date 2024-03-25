package com.example.quiz.dto;

import java.util.List;

public class SubjectWithQuestionsDTO {
    private Long subject_id;
    private String subject_title;
    private List<QuestionDTO> question_list;

    public SubjectWithQuestionsDTO(){}

    public SubjectWithQuestionsDTO(Long subject_id, String subject_title,List<QuestionDTO> question_list) {
        this.subject_id = subject_id;
        this.subject_title = subject_title;
        this.question_list = question_list;
    }

    public Long getSubject_id() {
        return subject_id;
    }
    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }

    public List<QuestionDTO> getQuestion_list() {
        return question_list;
    }
    public void setQuestion_list(List<QuestionDTO> question_list) {
        this.question_list = question_list;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    
    
}

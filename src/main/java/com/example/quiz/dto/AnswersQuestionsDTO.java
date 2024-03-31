package com.example.quiz.dto;

import java.util.List;

public class AnswersQuestionsDTO {
    private Long questionId;
    private String questionTitle;
    private List<AnswerDTO> answerList;

    public AnswersQuestionsDTO(){}

    public AnswersQuestionsDTO(Long questionId, String questionTitle, List<AnswerDTO> answerList) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.answerList = answerList;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public List<AnswerDTO> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerDTO> answerList) {
        this.answerList = answerList;
    }
}

package com.example.quiz.dto;

public class QuestionDTO {
    
    private Long id;
    private String title;
    private int points;
    private Long subject;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String title, int points) {
        this.id = id;
        this.title = title;
        this.points = points;
    }

    public QuestionDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public QuestionDTO(Long id, String title, int points, Long subject) {
        this.id = id;
        this.title = title;
        this.points = points;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

}

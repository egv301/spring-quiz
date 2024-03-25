package com.example.quiz.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quiz_status")
public class QuizStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="quiz_start")
    private Timestamp quiz_start;

    @Column(name="quiz_end")
    private Timestamp quiz_end;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getQuiz_start() {
        return quiz_start;
    }

    public void setQuiz_start(Timestamp quiz_start) {
        this.quiz_start = quiz_start;
    }

    public Timestamp getQuiz_end() {
        return quiz_end;
    }

    public void setQuiz_end(Timestamp quiz_end) {
        this.quiz_end = quiz_end;
    } 
}

package com.example.quiz.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne()
    @JoinColumn(name="subject_id")
    private Subject subject;
    
    @ManyToOne()
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne()
    @JoinColumn(name="answer_id")
    private Answer answer;

    public UserAnswer() {}
    
    public UserAnswer(User user, Subject subject, Question question, Answer answer) {
		this.user = user;
		this.subject = subject;
		this.question = question;
		this.answer = answer;
	}
    
    public UserAnswer(User user, Subject subject, Question question) {
		this.user = user;
		this.subject = subject;
		this.question = question;
	}

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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "UserAnswer [id=" + id + ", user=" + user.getUsername() + ", subject=" + subject.getTitle() + ", question=" + question.getTitle()
				+ ", answer=" + answer.getTitle() + "]";
	}
}

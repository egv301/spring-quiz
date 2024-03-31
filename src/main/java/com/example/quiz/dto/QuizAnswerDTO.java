package com.example.quiz.dto;

import javax.validation.constraints.NotNull;

public class QuizAnswerDTO {
	
	@NotNull(message = "Subject cannot be null")
	private Long subjectId;
	@NotNull(message = "Subject cannot be null")
	private Long questionId;
	@NotNull(message = "Subject cannot be null")
	private Long answerId;
	
	public QuizAnswerDTO() {}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	@Override
	public String toString() {
		return "QuizAnswerDTO [subjectId=" + subjectId + ", questionId=" + questionId + ", answerId=" + answerId + "]";
	}
}

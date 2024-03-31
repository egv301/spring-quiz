package com.example.quiz.dto;

import com.example.quiz.enums.AnswerStatus;

public class AnswerWithStatusDTO {
	private Long answerId;
	private String answerTitle;
    private AnswerStatus answerStatus;
    
    public AnswerWithStatusDTO() {}
    
    public AnswerWithStatusDTO(Long answerId, String answerTitle, AnswerStatus answerStatus) {
		this.answerId = answerId;
		this.answerTitle = answerTitle;
		this.answerStatus = answerStatus;
	}
    
    public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getAnswerTitle() {
		return answerTitle;
	}

	public void setAnswerTitle(String answerTitle) {
		this.answerTitle = answerTitle;
	}

	public AnswerStatus getAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(AnswerStatus answerStatus) {
		this.answerStatus = answerStatus;
	}

	@Override
	public String toString() {
		return "AnswerWithStatusDTO [answerId=" + answerId + ", answerTitle=" + answerTitle + ", answerStatus="
				+ answerStatus + "]";
	}
    
    
	
	
	
}

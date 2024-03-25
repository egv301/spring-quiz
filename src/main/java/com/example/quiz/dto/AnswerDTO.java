package com.example.quiz.dto;


public class AnswerDTO {
	private Long answerId;
	private String answerTitle;
    private boolean isCorrect;
    
    public AnswerDTO() {}

	public AnswerDTO(Long answerId, String answerTitle, boolean isCorrect) {
		this.answerId = answerId;
		this.answerTitle = answerTitle;
		this.isCorrect = isCorrect;
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

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public String toString() {
		return "AnswerDTO [answerId=" + answerId + ", answerTitle=" + answerTitle + ", isCorrect=" + isCorrect + "]";
	}
	
	
}

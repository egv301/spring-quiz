package com.example.quiz.dto;


public class AnswerDTO {
	private Long answerId;
	private String answerTitle;
    private boolean correct;
    
    public AnswerDTO() {}

	public AnswerDTO(Long answerId, String answerTitle, boolean correct) {
		this.answerId = answerId;
		this.answerTitle = answerTitle;
		this.correct = correct;
	}
	
	public AnswerDTO(String answerTitle) {
		this.answerTitle = answerTitle;
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
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public String toString() {
		return "AnswerDTO [answerId=" + answerId + ", answerTitle=" + answerTitle + ", correct=" + correct + "]";
	}
	
	
}

package com.example.quiz.dto;

import java.util.List;

public class QuestionAnswersDTO {
	private Long questionId;
    private String questionTitle;
    private List<AnswerDTO> answers;
    
    public QuestionAnswersDTO() {}

	public QuestionAnswersDTO(Long questionId, String questionTitle, List<AnswerDTO> answers) {
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.answers = answers;
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

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "QuestionAnswersDTO [questionId=" + questionId + ", questionTitle=" + questionTitle + ", answers="
				+ answers + "]";
	}
}

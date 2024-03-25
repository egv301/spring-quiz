package com.example.quiz.dto;

import java.util.List;

public class QuizDTO {
	private Long subjectId;
	private String subjectTitle;
	private List<QuestionAnswersDTO> questionAnswers;
	
	public QuizDTO() {}

	public QuizDTO(Long subjectId, String subjectTitle, List<QuestionAnswersDTO> questionAnswers) {
		this.subjectId = subjectId;
		this.subjectTitle = subjectTitle;
		this.questionAnswers = questionAnswers;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public List<QuestionAnswersDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswersDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	@Override
	public String toString() {
		return "QuizDTO [subjectId=" + subjectId + ", subjectTitle=" + subjectTitle + ", questionAnswers="
				+ questionAnswers + "]";
	}
	
	
}

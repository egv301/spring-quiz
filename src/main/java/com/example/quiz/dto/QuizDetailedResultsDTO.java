package com.example.quiz.dto;

import java.util.List;

public class QuizDetailedResultsDTO {
	
	private Long subjectId;
	private String subjectTitle;
	private List<QuestionAnswersResultsDTO> questionAnswers;
	
	@SuppressWarnings("unused")
	public QuizDetailedResultsDTO() {}

	public QuizDetailedResultsDTO(Long subjectId, String subjectTitle,
			List<QuestionAnswersResultsDTO> questionAnswers) {
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

	@SuppressWarnings("unused")
	public String getSubjectTitle() {
		return subjectTitle;
	}

	@SuppressWarnings("unused")
	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	@SuppressWarnings("unused")
	public List<QuestionAnswersResultsDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	@SuppressWarnings("unused")
	public void setQuestionAnswers(List<QuestionAnswersResultsDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
}

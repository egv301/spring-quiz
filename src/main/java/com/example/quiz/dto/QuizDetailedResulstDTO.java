package com.example.quiz.dto;

import java.util.List;

public class QuizDetailedResulstDTO {
	
	private Long subjectId;
	private String subjectTitle;
	private List<QuestionAnswersResultsDTO> questionAnswers;
	
	public QuizDetailedResulstDTO() {}

	public QuizDetailedResulstDTO(Long subjectId, String subjectTitle,
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

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public List<QuestionAnswersResultsDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswersResultsDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
}

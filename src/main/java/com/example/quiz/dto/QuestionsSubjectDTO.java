package com.example.quiz.dto;

import java.util.List;

public class QuestionsSubjectDTO {
	
	private Long subjectId;
	private String subjectTitle;
	private List<QuestionDTO> questionList;
	
	public QuestionsSubjectDTO() {}
	
	public QuestionsSubjectDTO(Long subjectId, String subjectTitle, List<QuestionDTO> questionList) {
		this.subjectId = subjectId;
		this.subjectTitle = subjectTitle;
		this.questionList = questionList;
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

	public List<QuestionDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionDTO> questionList) {
		this.questionList = questionList;
	}
}

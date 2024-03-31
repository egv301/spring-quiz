package com.example.quiz.dto;

public class QuizResultDTO {
	
	private Long subjectId;
	private String subjectTitle;
	private int quizScore;
	
	public QuizResultDTO() {}

	public QuizResultDTO(Long subjectId, String subjectTitle, int quizScore) {
		this.subjectId = subjectId;
		this.subjectTitle = subjectTitle;
		this.quizScore = quizScore;
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

	public int getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}
}

package com.example.quiz.dto;

public class SubjectListDTO {
	
	private Long subjectId;
	private String subjectTitle;
	private boolean canPass;
	
	public SubjectListDTO(Long subjectId, String subjectTitle, boolean canPass) {
		this.subjectId = subjectId;
		this.subjectTitle = subjectTitle;
		this.canPass = canPass;
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

	public boolean isCanPass() {
		return canPass;
	}

	public void setCanPass(boolean canPass) {
		this.canPass = canPass;
	}
}

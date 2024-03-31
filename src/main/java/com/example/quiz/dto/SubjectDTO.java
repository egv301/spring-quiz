package com.example.quiz.dto;

import javax.validation.constraints.NotBlank;

public class SubjectDTO {
	
	private Long id;
	@NotBlank(message="Title should not be empty")
	private String title;
	private boolean isActive;

	public SubjectDTO() {}

	public SubjectDTO(Long id,String title) {
		this.id = id;
		this.title = title;
	}

	public SubjectDTO(Long id,String title, boolean isActive) {
		this.id = id;
		this.title = title;
		this.isActive = isActive;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}

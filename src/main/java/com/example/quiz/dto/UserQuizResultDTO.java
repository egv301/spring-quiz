package com.example.quiz.dto;

public class UserQuizResultDTO {
	
	private Long userId;
	private String userName;
	private int score;
	
	public UserQuizResultDTO() {}
	
	public UserQuizResultDTO(Long userId, String userName, int score) {
		this.userId = userId;
		this.userName = userName;
		this.score = score;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UserQuizResultDTO [userId=" + userId + ", userName=" + userName + ", score=" + score + "]";
	}
}

package com.example.quiz.dto;
import java.util.List;

public class QuestionAnswersResultsDTO {
	
	private Long questionId;
    private String questionTitle;
    private List<AnswerWithStatusDTO> answers;
    
    public QuestionAnswersResultsDTO() {}

	public QuestionAnswersResultsDTO(Long questionId, String questionTitle, List<AnswerWithStatusDTO> answers) {
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

	public List<AnswerWithStatusDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerWithStatusDTO> answers) {
		this.answers = answers;
	}
}


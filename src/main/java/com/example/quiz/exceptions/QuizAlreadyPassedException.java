package com.example.quiz.exceptions;

public class QuizAlreadyPassedException extends Exception {
	public QuizAlreadyPassedException(String message) {
		super(message);
	}
}

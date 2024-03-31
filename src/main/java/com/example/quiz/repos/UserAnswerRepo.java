package com.example.quiz.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;
import com.example.quiz.models.User;
import com.example.quiz.models.UserAnswer;

public interface UserAnswerRepo extends JpaRepository<UserAnswer, Long> {
	List<UserAnswer> findByUserAndSubject(User user, Subject subject);
	
	@Query(value = "select ua.answer.id from UserAnswer ua where ua.user = :user AND ua.subject = :subject")
	List<Long> getUserAnswersIds(@Param("user") User user, @Param("subject") Subject subject);

	Optional<UserAnswer> findByUserAndSubjectAndQuestion(User user, Subject subject, Question question);
}
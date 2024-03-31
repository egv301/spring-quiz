package com.example.quiz.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.models.Answer;
import com.example.quiz.models.Question;
import com.example.quiz.models.Subject;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
	@Query(value="SELECT e.id from Answer e where e.question in :questionList")
	List<Long> getAnswerIds(List<Question> questionList);
	
	@Transactional
	@Modifying
	@Query("update Answer a set a.correct = false where a.id != :answerId")
	void setOtherAnwersToExcept(@Param("answerId") Long answerId);
	
	@Query("select a from Answer a where a.question.subject = :subject")
	List<Answer> getAnswersBySubject(@Param("subject") Subject subject);
	
	@Query("select a from Answer a where a.question.subject = :subject AND a.correct=true")
	List<Answer> getCorrectAnswersBySubject(@Param("subject") Subject subject);
	
	
	
    
}

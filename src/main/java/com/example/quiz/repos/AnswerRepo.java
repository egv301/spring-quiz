package com.example.quiz.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.quiz.domain.Answer;
import com.example.quiz.domain.Question;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
	@Query(value="SELECT e.id from Answer e where e.question in :questionList")
	List<Long> getAnswerIds(List<Question> questionList);
    
}

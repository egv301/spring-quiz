package com.example.quiz.repos;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quiz.domain.QuizResult;
import com.example.quiz.domain.Subject;
import com.example.quiz.domain.User;
import com.example.quiz.dto.UserQuizResultDTO;
import com.example.quiz.projections.IQuizCount;


public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
	
	List<QuizResult> findBySubject(Subject subject);
	@Query(value="SELECT s.id AS subjectId,s.title AS subjectTitle,count(*) AS Count " 
			     +"FROM subject s INNER JOIN quiz_result q " 
			     +"ON s.id = q.quiz_id GROUP BY s.id",nativeQuery=true)
    List<IQuizCount> countQuizes();
	
	List<QuizResult> findBySubjectOrderByResultDesc(Subject subject);
	List<QuizResult> findByUserOrderByResultDesc(User user);
	
	boolean existsByUserAndSubject(User user, Subject subject);

	@Query(value = "SELECT e.subject.id FROM QuizResult e where e.user = :user")     
	List<Long> getSubjectIDS(@Param("user") User user);
	
	
	
}

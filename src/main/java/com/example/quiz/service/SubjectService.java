package com.example.quiz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz.domain.Question;
import com.example.quiz.domain.Subject;
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.dto.SubjectDTO;
import com.example.quiz.dto.SubjectWithQuestionsDTO;
import com.example.quiz.exceptions.NotFoundException;
import com.example.quiz.repos.SubjectRepo;

@Service
public class SubjectService {
	@Autowired
    private SubjectRepo subjectRepo;
	
	public Subject getSubject(Long subject_id) throws NotFoundException{
		return subjectRepo.findById(subject_id).orElseThrow(()->new NotFoundException("Subject not found"));
	}

	public SubjectDTO getSubjectDTO(Long question_id) throws NotFoundException{
		Subject subject = getSubject(question_id);
		return new SubjectDTO(
			subject.getId(),
			subject.getTitle(),
			subject.getIsActive()
		);
	}

	public List<Subject> subjectList(){
		return subjectRepo.findAll();
	}

	public List<SubjectDTO> subjectListDTO(){
		return subjectRepo.findAll().stream().map(subject->new SubjectDTO(subject.getId(), subject.getTitle(), subject.getIsActive())
		).collect(Collectors.toList());
	}
	
	public void saveSubject(SubjectDTO subjectDto) throws NotFoundException {
		subjectRepo.save(new Subject(
			subjectDto.getTitle(),
			subjectDto.getIsActive()
		));
	}

	public void updateSubject(Long subject_id,SubjectDTO subjectDto) throws NotFoundException {
		Subject subject = getSubject(subject_id);
		subject.setTitle(subjectDto.getTitle());
		subject.setActive(subjectDto.getIsActive());
		subjectRepo.save(subject);
	}

	public SubjectWithQuestionsDTO getSubjectWithQuestions(Long subject_id) throws NotFoundException{
		Subject subject = getSubject(subject_id);
		List<QuestionDTO> questionList = subject
										.getQuestions()
										.stream()
										.map(question->new QuestionDTO(
												question.getId(),
												question.getTitle()))
										.collect(Collectors.toList());
		return new SubjectWithQuestionsDTO(subject.getId(),subject.getTitle(),questionList);
		

	}
	public void removeSubject(Long subject_id) {
		subjectRepo.deleteById(subject_id);
	}
}

package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.ReadingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReadingQuestionRepository extends JpaRepository<ReadingQuestion, Long> {
    List<ReadingQuestion> findByDeletedFalseAndExam(Exam exam);
    Optional<ReadingQuestion> findByIdAndDeletedFalse(Long id);
}

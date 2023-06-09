package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.ListeningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IListeningQuestionRepository extends JpaRepository<ListeningQuestion, Long> {
    List<ListeningQuestion> findByDeletedFalseAndExam(Exam exam);
    Optional<ListeningQuestion> findByIdAndDeletedFalse(Long id);
}

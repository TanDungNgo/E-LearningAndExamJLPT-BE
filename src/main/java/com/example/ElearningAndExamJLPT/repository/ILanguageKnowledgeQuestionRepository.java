package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.LanguageKnowledgeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILanguageKnowledgeQuestionRepository extends JpaRepository<LanguageKnowledgeQuestion, Long> {
    List<LanguageKnowledgeQuestion> findByDeletedFalseAndExam(Exam exam);
    Optional<LanguageKnowledgeQuestion> findByIdAndDeletedFalse(Long id);
}
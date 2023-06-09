package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.LanguageKnowledgeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILanguageKnowledgeQuestionRepository extends JpaRepository<LanguageKnowledgeQuestion, Long> {
}

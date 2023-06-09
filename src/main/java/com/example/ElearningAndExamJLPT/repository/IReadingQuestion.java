package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.ReadingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReadingQuestion extends JpaRepository<ReadingQuestion, Long> {
}

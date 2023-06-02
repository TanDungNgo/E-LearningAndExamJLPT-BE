package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByDeletedFalseAndLevel(Level level);
    List<Exam> findAllByDeletedFalse();
    Optional<Exam> findExamByDeletedFalseAndId(Long id);
}

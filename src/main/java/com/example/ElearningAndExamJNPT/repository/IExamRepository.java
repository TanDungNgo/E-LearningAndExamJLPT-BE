package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExamRepository extends JpaRepository<Exam, Long> {
}

package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.ExamResult;
import com.example.ElearningAndExamJLPT.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findAllByStudentId(User studentId);
}

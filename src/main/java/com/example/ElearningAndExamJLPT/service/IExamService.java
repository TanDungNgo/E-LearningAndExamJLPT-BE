package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseExam;
import com.example.ElearningAndExamJLPT.dto.response.ResponseExamResult;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.ExamResult;
import com.example.ElearningAndExamJLPT.entity.User.User;

import java.util.List;

public interface IExamService extends IService<Exam,Long>{
    ResponseExam getExam(Exam exam);
    ResponseExam getRandomExam(String level);
    ResponseExamResult submitExam(int[] answers, Exam exam);
    List<ExamResult> getAllExamResult();
    List<ExamResult> getAllExamResultByStudentId();
    ResponseExamResult getExamResultById(Long id);
}

package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseExam;
import com.example.ElearningAndExamJLPT.entity.Exam;

public interface IExamService extends IService<Exam,Long>{
    ResponseExam getExam(Exam exam);
}

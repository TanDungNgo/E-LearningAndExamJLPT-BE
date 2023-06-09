package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.entity.Exam;

import java.util.List;

public interface IQuestionExamService {
    Object saveQuestionExam(QuestionDTO questionDTO);
    List<ResponseQuestion> getAllQuestionExam(Exam exam);
}

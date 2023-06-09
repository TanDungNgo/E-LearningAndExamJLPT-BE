package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;

public interface IQuestionExamService {
    Object saveQuestionExam(QuestionDTO questionDTO);
}

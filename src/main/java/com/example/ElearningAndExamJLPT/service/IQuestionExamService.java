package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.entity.Exam;

import java.util.List;
import java.util.Optional;

public interface IQuestionExamService {
    Object saveQuestionExam(QuestionDTO questionDTO);
    List<ResponseQuestion> getAllQuestionExam(Exam exam);
    ResponseQuestion getKnowledgeQuestionById(Long id);
    ResponseQuestion getListeningQuestionById(Long id);
    ResponseQuestion getReadingQuestionById(Long id);
    void deleteKnowledgeQuestionById(Long id);
    void deleteListeningQuestionById(Long id);
    void deleteReadingQuestionById(Long id);
}

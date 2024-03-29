package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseExamResult {
    private String examName;
    private int totalQuestion;
    private int correctAnswer;
    private int percentage;
    private String status;
    private String examDate;
    private int totalLanguageKnowledgeQuestion;
    private int correctAnswerLanguageKnowledgeQuestion;
    private int totalReadingQuestion;
    private int correctAnswerReadingQuestion;
    private int totalListeningQuestion;
    private int correctAnswerListeningQuestion;
    private List<ResponseQuestion> languageKnowledgeQuestions;
    private List<ResponseQuestion> readingQuestions;
    private List<ResponseQuestion> listeningQuestions;
}

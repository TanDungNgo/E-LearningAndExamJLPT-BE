package com.example.ElearningAndExamJLPT.controller;


import com.example.ElearningAndExamJLPT.dto.response.ResponseExamResult;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.LanguageKnowledgeQuestion;
import com.example.ElearningAndExamJLPT.entity.ListeningQuestion;
import com.example.ElearningAndExamJLPT.entity.ReadingQuestion;
import com.example.ElearningAndExamJLPT.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamServiceImpl examService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllExam() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam successfully", examService.getAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Exam> foundExam = examService.getById(id);
        return foundExam.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query exam successfully", examService.getExam(foundExam.get()))
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find exam with id = " + id, "")
                );
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<ResponseObject> submitExam(@RequestBody int[] answers, @PathVariable("id") Long id) {
        Optional<Exam> foundExam = examService.getById(id);
        if (!foundExam.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find exam with id = " + id, "")
            );
        }
        List<LanguageKnowledgeQuestion> languageKnowledgeQuestions = foundExam.get().getLanguageKnowledgeQuestions();
        int[] correctAnswerLanguageKnowledgeQuestion = new int[languageKnowledgeQuestions.size()];

        List<ReadingQuestion> readingQuestions = foundExam.get().getReadingQuestions();
        int[] correctAnswerReadingQuestion = new int[readingQuestions.size()];

        List<ListeningQuestion> listeningQuestions = foundExam.get().getListeningQuestions();
        int[] correctAnswerListeningQuestion = new int[listeningQuestions.size()];

        for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
            correctAnswerLanguageKnowledgeQuestion[i] = languageKnowledgeQuestions.get(i).getCorrectAnswer();
        }
        for (int i = 0; i < readingQuestions.size(); i++) {
            correctAnswerReadingQuestion[i] = readingQuestions.get(i).getCorrectAnswer();
        }
        for (int i = 0; i < listeningQuestions.size(); i++) {
            correctAnswerListeningQuestion[i] = listeningQuestions.get(i).getCorrectAnswer();
        }
        int totalCorrectLanguageKnowledgeQuestion = 0;
        for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
            if (answers[i] == correctAnswerLanguageKnowledgeQuestion[i]) {
                totalCorrectLanguageKnowledgeQuestion++;
            }
        }
        int totalCorrectReadingQuestion = 0;
        for (int i = 0; i < readingQuestions.size(); i++) {
            if (answers[i + languageKnowledgeQuestions.size()] == correctAnswerReadingQuestion[i]) {
                totalCorrectReadingQuestion++;
            }
        }
        int totalCorrectListeningQuestion = 0;
        for (int i = 0; i < listeningQuestions.size(); i++) {
            if (answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()] == correctAnswerListeningQuestion[i]) {
                totalCorrectListeningQuestion++;
            }
        }

        int totalCorrectAnswer = totalCorrectLanguageKnowledgeQuestion + totalCorrectReadingQuestion + totalCorrectListeningQuestion;

        double percentage = (double) totalCorrectAnswer / answers.length * 100;

        ResponseExamResult responseExamResult = new ResponseExamResult();
        responseExamResult.setExamName(foundExam.get().getName());
        responseExamResult.setTotalLanguageKnowledgeQuestion(languageKnowledgeQuestions.size());
        responseExamResult.setCorrectAnswerLanguageKnowledgeQuestion(totalCorrectLanguageKnowledgeQuestion);
        responseExamResult.setTotalReadingQuestion(readingQuestions.size());
        responseExamResult.setCorrectAnswerReadingQuestion(totalCorrectReadingQuestion);
        responseExamResult.setTotalListeningQuestion(listeningQuestions.size());
        responseExamResult.setCorrectAnswerListeningQuestion(totalCorrectListeningQuestion);
        responseExamResult.setCorrectAnswer(totalCorrectAnswer);
        int totalQuestion = languageKnowledgeQuestions.size() + readingQuestions.size() + listeningQuestions.size();
        responseExamResult.setTotalQuestion(totalQuestion);
        responseExamResult.setPercentage((int) Math.round(percentage));
        responseExamResult.setExamDate(String.valueOf(LocalDateTime.now()));
        if (percentage >= 50) {
            responseExamResult.setStatus("Pass");
        } else {
            responseExamResult.setStatus("Fail");
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Submit exam successfully", responseExamResult)
        );
    }
}

package com.example.ElearningAndExamJLPT.controller;


import com.example.ElearningAndExamJLPT.dto.response.ResponseExamResult;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
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
import java.util.ArrayList;
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
        List<ResponseQuestion> languageKnowledge = new ArrayList<>();
        List<ResponseQuestion> reading = new ArrayList<>();
        List<ResponseQuestion> listening = new ArrayList<>();
        for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(languageKnowledgeQuestions.get(i).getId());
            responseQuestion.setTitle(languageKnowledgeQuestions.get(i).getTitle());
            responseQuestion.setText(languageKnowledgeQuestions.get(i).getText());
            responseQuestion.setOption1(languageKnowledgeQuestions.get(i).getOption1());
            responseQuestion.setOption2(languageKnowledgeQuestions.get(i).getOption2());
            responseQuestion.setOption3(languageKnowledgeQuestions.get(i).getOption3());
            responseQuestion.setOption4(languageKnowledgeQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(languageKnowledgeQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i]);
            languageKnowledge.add(responseQuestion);
            correctAnswerLanguageKnowledgeQuestion[i] = languageKnowledgeQuestions.get(i).getCorrectAnswer();

        }
        for (int i = 0; i < readingQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(readingQuestions.get(i).getId());
            responseQuestion.setTitle(readingQuestions.get(i).getTitle());
            responseQuestion.setText(readingQuestions.get(i).getText());
            responseQuestion.setImage(readingQuestions.get(i).getImage());
            responseQuestion.setOption1(readingQuestions.get(i).getOption1());
            responseQuestion.setOption2(readingQuestions.get(i).getOption2());
            responseQuestion.setOption3(readingQuestions.get(i).getOption3());
            responseQuestion.setOption4(readingQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(readingQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size()]);
            reading.add(responseQuestion);
            correctAnswerReadingQuestion[i] = readingQuestions.get(i).getCorrectAnswer();
        }
        for (int i = 0; i < listeningQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(listeningQuestions.get(i).getId());
            responseQuestion.setTitle(listeningQuestions.get(i).getTitle());
            responseQuestion.setText(listeningQuestions.get(i).getText());
            responseQuestion.setImage(listeningQuestions.get(i).getImage());
            responseQuestion.setAudioFile(listeningQuestions.get(i).getAudioFile());
            responseQuestion.setOption1(listeningQuestions.get(i).getOption1());
            responseQuestion.setOption2(listeningQuestions.get(i).getOption2());
            responseQuestion.setOption3(listeningQuestions.get(i).getOption3());
            responseQuestion.setOption4(listeningQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(listeningQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()]);
            listening.add(responseQuestion);
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
        responseExamResult.setLanguageKnowledgeQuestions(languageKnowledge);
        responseExamResult.setReadingQuestions(reading);
        responseExamResult.setListeningQuestions(listening);
        if (percentage >= 50) {
            responseExamResult.setStatus("Pass");
        } else {
            responseExamResult.setStatus("Fail");
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Submit exam successfully", responseExamResult)
        );
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<ResponseObject> getRandomExamByLevel(@PathVariable("level") String level) {
        if (examService.getRandomExam(level) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find exam with level = " + level, examService.getRandomExam(level))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam successfully", examService.getRandomExam(level))
        );
    }

}

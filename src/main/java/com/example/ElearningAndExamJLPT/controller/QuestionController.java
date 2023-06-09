package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.service.impl.ExamServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.QuestionExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionExamServiceImpl questionExamService;
    @Autowired
    private ExamServiceImpl examService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> saveQuestionExam(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Insert question successfully", questionExamService.saveQuestionExam(questionDTO))
        );
    }
    @GetMapping("/exam/{id}")
    public ResponseEntity<ResponseObject> getAllQuestionExam(@PathVariable("id") Long id) {
        Optional<Exam> foundExam = examService.getById(id);
        if (!foundExam.isPresent()) {
            return ResponseEntity.badRequest().body(
                    new ResponseObject("failed", "Cannot find exam with id = " + id, "")
            );
        }
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Query question successfully", questionExamService.getAllQuestionExam(foundExam.get()))
        );
    }

}

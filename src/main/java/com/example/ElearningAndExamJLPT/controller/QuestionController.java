package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.QuestionDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.service.impl.QuestionExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionExamServiceImpl questionExamService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> saveQuestionExam(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Insert question successfully", questionExamService.saveQuestionExam(questionDTO))
        );
    }

}

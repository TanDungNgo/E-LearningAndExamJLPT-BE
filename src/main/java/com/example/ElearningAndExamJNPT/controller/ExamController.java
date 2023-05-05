package com.example.ElearningAndExamJNPT.controller;


import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.Exam;
import com.example.ElearningAndExamJNPT.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamServiceImpl examService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Optional<Exam> foundExam = examService.getById(id);
        return foundExam.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query exam successfully", foundExam)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find exam with id = " + id, "")
                );
    }
}

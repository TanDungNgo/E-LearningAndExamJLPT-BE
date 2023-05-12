package com.example.ElearningAndExamJLPT.controller;


import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamServiceImpl examService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllExam(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam successfully", examService.getAll())
        );
    }

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

    @PostMapping("/submit")
    public ResponseEntity<ResponseObject> submitExam(@RequestBody int[] answers){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Submit exam successfully", answers)
        );
    }
}

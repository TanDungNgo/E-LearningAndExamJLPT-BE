package com.example.ElearningAndExamJLPT.controller;


import com.example.ElearningAndExamJLPT.dto.ExamDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseExamResult;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.dto.response.ResponseUserExam;
import com.example.ElearningAndExamJLPT.entity.*;
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
        ResponseExamResult responseExamResult = examService.submitExam(answers, foundExam.get());
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

    @GetMapping("/exam-result/student")
    public ResponseEntity<?> getAllExamResultByStudent(){
        List<ExamResult> examResults = examService.getAllExamResultByStudentId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam result successfully", examResults)
        );
    }

    @GetMapping("/exam-result/all")
    public ResponseEntity<?> getAllExamResult(){
        List<ExamResult> examResults = examService.getAllExamResult();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam result successfully", examResults)
        );
    }
    @GetMapping("/exam-result/{id}")
    public ResponseEntity<?> getExamResultById(@PathVariable("id") Long id){
        ResponseExamResult examResult = examService.getExamResultById(id);
        return examResult != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query exam result successfully", examResult)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find exam result with id = " + id, "")
                );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createExam(@RequestBody ExamDTO examDTO) {
        Exam exam = new Exam();
        exam.setName(examDTO.getName());
        exam.setLevel(examDTO.getLevel());
        exam.setPrice(examDTO.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Create exam successfully", examService.save(exam))
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateExam(@RequestBody ExamDTO newExam, @PathVariable("id") Long id) {
        Optional<Exam> foundExam = examService.getById(id);
        if (!foundExam.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find exam with id = " + id, "")
            );
        }
        Exam exam = foundExam.get();
        exam.setId(id);
        exam.setName(newExam.getName());
        exam.setLevel(newExam.getLevel());
        exam.setPrice(newExam.getPrice());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update exam successfully", examService.update(exam))
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteExam(@PathVariable("id") Long id) {
        Optional<Exam> foundExam = examService.getById(id);
        if (!foundExam.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find exam with id = " + id, "")
            );
        }
        examService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete exam successfully", "")
        );
    }
    @GetMapping("/top-3/score")
    public ResponseEntity<ResponseObject> getTop3ExamResult(){
        List<ResponseUserExam> examResults = examService.getTop3ExamResult();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query exam result successfully", examResults)
        );
    }
}

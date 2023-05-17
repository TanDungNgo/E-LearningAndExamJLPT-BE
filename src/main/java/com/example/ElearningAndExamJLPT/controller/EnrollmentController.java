package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.service.impl.EnrollmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentServiceImpl enrollmentService;

    @PostMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseObject> enrollCourse(@PathVariable("id") Long id) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(id);
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Enroll course successfully", enrollmentService.save(enrollment))
        );
    }
}

package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.response.ResponMessage;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.service.impl.CourseServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.EnrollmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentServiceImpl enrollmentService;
    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping(value = "/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable("courseId") Long courseId) {
        boolean checkEnrolled = enrollmentService.existsByStudentIdAndCourseId(courseId);
        if (checkEnrolled == true) {
            return new ResponseEntity<>(new ResponMessage("Enrolled!"), HttpStatus.OK);
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseService.getById(courseId).get());
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Enroll course successfully", enrollmentService.save(enrollment)
                ));
    }

    @GetMapping(value = "/check/{courseId}")
    public ResponseEntity<?> checkEnrolledCourse(@PathVariable("courseId") Long courseId) {
        boolean checkEnrolled = enrollmentService.existsByStudentIdAndCourseId(courseId);
        if (checkEnrolled == true) {
            return new ResponseEntity<>(new ResponMessage("Enrolled!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponMessage("Not enrolled!"), HttpStatus.OK);
    }

    @GetMapping(value = "students/{courseId}")
    public ResponseEntity<?> getStudentsByCourse(@PathVariable("courseId") Long courseId) {
        Optional<Course> course = courseService.getById(courseId);
        if (course.isPresent()) {
            return ResponseEntity.ok().body(
                    new ResponseObject("ok", "Get enrolled course successfully", enrollmentService.getStudentsByCourse(course.get())
                    ));
        }
        return new ResponseEntity<>(new ResponMessage("Not enrolled!"), HttpStatus.OK);
    }
}

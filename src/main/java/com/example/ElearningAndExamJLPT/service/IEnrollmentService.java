package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.entity.User.User;

import java.util.Optional;

public interface IEnrollmentService extends IService<Enrollment, Long>{
    boolean existsByStudentIdAndCourseId(Long courseId);
}

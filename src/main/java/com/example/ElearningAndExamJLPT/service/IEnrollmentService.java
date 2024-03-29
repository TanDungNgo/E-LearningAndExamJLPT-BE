package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseCourse;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.entity.User.User;

import java.util.List;
import java.util.Map;

public interface IEnrollmentService extends IService<Enrollment, Long>{
    boolean existsByStudentIdAndCourseId(Long courseId);
    List<User> getStudentsByCourse(Course course);
    List<ResponseCourse> getCoursesByStudent();
    Enrollment enrollCourse(Long courseId,Enrollment enrollment);
    Map<String, List<Enrollment>> getMonthlyStudentCount();
}

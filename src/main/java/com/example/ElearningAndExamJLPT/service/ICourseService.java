package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.response.ResponseCourse;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICourseService extends IService<Course,Long> {
    List<ResponseCourse> searchCourses(String query, String type, String level);
    List<ResponseCourse> findAll(Pageable pageable);
    List<ResponseCourse> getAllCourse();
    ResponseCourse getCourse(Course course);
    List<ResponseCourse> getSuggestedCourses();
    void rateCourse(Course course, double rating);
    List<ResponseCourse> getPopularCourses();
    List<ResponseCourse> getMyCourse();
    List<ResponseCourse> getNewCourses();
    public Map<Level, Long> getCountCoursesByLevel();
}

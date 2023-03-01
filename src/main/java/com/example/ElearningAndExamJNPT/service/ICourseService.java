package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService extends IService<Course,Long> {
    List<Course> searchCourses(String query);
    Page<Course> findAll(Pageable pageable);
}

package com.example.ElearningAndExamJNPT.service;

import com.example.ElearningAndExamJNPT.entity.Course;

import java.util.List;

public interface ICourseService extends IService<Course,Long> {
    List<Course> searchCourses(String query);
}

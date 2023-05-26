package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseRatingRepository extends JpaRepository<CourseRating, Long>{
    List<CourseRating> findByCourse(Course course);
}

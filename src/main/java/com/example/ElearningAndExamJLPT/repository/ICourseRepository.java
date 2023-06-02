package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE " +
            "(c.name LIKE CONCAT('%', :query, '%') OR c.description LIKE CONCAT('%', :query, '%'))" +
            "AND c.deleted = false")
    List<Course> searchCourses(String query);

    Page<Course> findAll(Pageable pageable);
    List<Course> findAllByDeletedFalse();
    Optional<Course> findCourseByDeletedFalseAndId(Long id);
}

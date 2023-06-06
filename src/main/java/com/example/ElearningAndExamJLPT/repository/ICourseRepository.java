package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Level;
import com.example.ElearningAndExamJLPT.entity.User.User;
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
            "AND (:type IS NULL OR c.type = :type) " +
            "AND (:level IS NULL OR c.level = :level OR (:type IS NULL AND :level IS NULL)) " +
            "AND c.deleted = false")
    List<Course> searchCourses(String query, String type, Level level);

    Page<Course> findAll(Pageable pageable);

    List<Course> findAllByDeletedFalse();

    Optional<Course> findCourseByDeletedFalseAndId(Long id);

    List<Course> findAllByDeletedFalseAndCreatedBy(User user);
}

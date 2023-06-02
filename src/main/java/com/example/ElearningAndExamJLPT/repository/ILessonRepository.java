package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE " +
            "l.name LIKE CONCAT('%',:query, '%')" +
            "Or l.description LIKE CONCAT('%', :query, '%')"+
            "AND l.deleted = false")
    List<Lesson> searchLessons(String query);
    List<Lesson> findAllByDeletedFalse();
    Optional<Lesson> findLessonByDeletedFalseAndId(Long id);
}

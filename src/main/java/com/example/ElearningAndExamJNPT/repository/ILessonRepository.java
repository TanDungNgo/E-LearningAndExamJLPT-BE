package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE " +
            "l.name LIKE CONCAT('%',:query, '%')" +
            "Or l.description LIKE CONCAT('%', :query, '%')")
    List<Lesson> searchLessons(String query);
}

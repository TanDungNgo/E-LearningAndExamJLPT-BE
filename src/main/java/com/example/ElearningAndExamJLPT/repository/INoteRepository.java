package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.Note;
import com.example.ElearningAndExamJLPT.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByDeletedFalseAndLessonAndUser(Lesson lesson, User user);
    Optional<Note> findNoteByDeletedFalseAndId(Long id);
}
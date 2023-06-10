package com.example.ElearningAndExamJLPT.service;

import com.example.ElearningAndExamJLPT.dto.NoteDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseLesson;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.Note;

import java.util.List;

public interface ILessonService extends IService<Lesson, Long> {
    List<Lesson> searchLessons(String query);
    Lesson markVideoAsWatched(Lesson lesson);
    ResponseLesson getLesson(Lesson lesson);
    List<ResponseLesson> getAllByCourse(Course course);
    Note addNoteToLesson(Lesson lesson, NoteDTO note);
    List<Note> getAllNotesByLesson(Lesson lesson);
    Note updateNoteInLesson(Lesson lesson, NoteDTO note);
    void deleteNoteInLesson(Long id);

}
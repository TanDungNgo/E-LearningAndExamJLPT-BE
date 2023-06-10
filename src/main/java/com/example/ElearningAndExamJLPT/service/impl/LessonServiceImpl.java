package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.NoteDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseLesson;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.Note;
import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.RoleName;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.entity.UserLesson;
import com.example.ElearningAndExamJLPT.repository.*;
import com.example.ElearningAndExamJLPT.service.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserLessonRepository userLessonRepository;
    @Autowired
    private INoteRepository noteRepository;

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findLessonByDeletedFalseAndId(id);
    }

    @Override
    public Lesson save(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public List<Lesson> searchLessons(String query) {
        return lessonRepository.searchLessons(query);
    }

    @Override
    public Lesson markVideoAsWatched(Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        UserLesson userLesson = userLessonRepository.findByLessonAndUser(lesson, user);
        if (userLesson != null) {
            userLesson.setCompleted(true);
            userLessonRepository.save(userLesson);
            return lesson;
        }
        return null;
    }

    @Override
    public ResponseLesson getLesson(Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        ResponseLesson responseLesson = new ResponseLesson();
        if(hasRole(user, RoleName.TEACHER) || hasRole(user, RoleName.ADMIN)) {
            responseLesson.setId(lesson.getId());
            responseLesson.setName(lesson.getName());
            responseLesson.setDescription(lesson.getDescription());
            responseLesson.setRate(lesson.getRate());
            responseLesson.setCompleted(true);
            responseLesson.setUrlVideo(lesson.getUrlVideo());
        }
        else {
            UserLesson userLesson = userLessonRepository.findByLessonAndUser(lesson, user);
            responseLesson.setId(lesson.getId());
            responseLesson.setName(lesson.getName());
            responseLesson.setDescription(lesson.getDescription());
            responseLesson.setRate(lesson.getRate());
            responseLesson.setCompleted(userLesson != null && userLesson.isCompleted());
            if (userLesson != null) {
                responseLesson.setUrlVideo(userLesson.isCompleted() ? lesson.getUrlVideo() : null);
            }
        }
        return responseLesson;
    }

    @Override
    public List<ResponseLesson> getAllByCourse(Course course) {
        List<Lesson> lessons = lessonRepository.findAllByDeletedFalseAndCourse(course);
        List<ResponseLesson> responseLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            ResponseLesson responseLesson = new ResponseLesson();
            responseLesson.setId(lesson.getId());
            responseLesson.setName(lesson.getName());
            responseLesson.setDescription(lesson.getDescription());
            responseLesson.setUrlVideo(lesson.getUrlVideo());
            responseLessons.add(responseLesson);
        }
        return responseLessons;
    }

    @Override
    public Lesson update(Lesson entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return lessonRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Lesson lesson = lessonRepository.findLessonByDeletedFalseAndId(id).get();
        lesson.setDeleted(true);
        lessonRepository.save(lesson);
//        lessonRepository.deleteById(id);
    }
    @Override
    public Note addNoteToLesson(Lesson lesson, NoteDTO note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        Note newNote = new Note();
        newNote.setLesson(lesson);
        newNote.setContent(note.getContent());
        newNote.setTime(note.getTime());
        newNote.setUser(user);
        return noteRepository.save(newNote);
    }

    @Override
    public List<Note> getAllNotesByLesson(Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        return noteRepository.findAllByDeletedFalseAndLessonAndUser(lesson, user);
    }

    @Override
    public Note updateNoteInLesson(Lesson lesson, NoteDTO note) {
        return null;
    }

    @Override
    public void deleteNoteInLesson(Long id) {
        Note note = noteRepository.findNoteByDeletedFalseAndId(id).get();
        note.setDeleted(true);
        noteRepository.save(note);
    }

    public boolean hasRole(User user, RoleName roleName) {
        for (Role role : user.getRoles()) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
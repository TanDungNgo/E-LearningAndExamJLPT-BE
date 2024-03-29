package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.LessonDTO;
import com.example.ElearningAndExamJLPT.dto.NoteDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.service.impl.CourseServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonServiceImpl lessonService;
    @Autowired
    private CourseServiceImpl courseService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllLesson() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", lessonService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setName(lessonDTO.getName());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setUrlVideo(lessonDTO.getUrlVideo());
        lesson.setCourse(courseService.getById(lessonDTO.getCourse_id()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert lesson successfully", lessonService.save(lesson))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        return foundLesson.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query lesson successfully", lessonService.getLesson(foundLesson.get())
                        )) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody LessonDTO newLesson) {
        Lesson updatedLesson = lessonService.getById(id)
                .map(lesson -> {
                    lesson.setName(newLesson.getName());
                    lesson.setDescription(newLesson.getDescription());
                    lesson.setUrlVideo(newLesson.getUrlVideo());
                    return lessonService.update(lesson);
                }).orElseGet(()->{
                    Lesson lesson = new Lesson();
                    lesson.setName(newLesson.getName());
                    lesson.setDescription(newLesson.getDescription());
                    lesson.setUrlVideo(newLesson.getUrlVideo());
                    lesson.setId(id);
                    return lessonService.save(lesson);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update lesson successfully", updatedLesson)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            lessonService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete lesson successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Lesson>> searchLessons(@RequestParam("query") String query){
        return ResponseEntity.ok(lessonService.searchLessons(query));
    }
    @PutMapping("/watched/{id}")
    public ResponseEntity<?> markVideoAsWatched(@PathVariable("id") Long id){
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            lessonService.markVideoAsWatched(lessonService.getById(id).get());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Completed lesson" , "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
    @GetMapping("/course/{id}")
    public ResponseEntity<?> getAllByCourse(@PathVariable("id") Long id){
        Optional<Course> foundCourse = courseService.getById(id);
        if (foundCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query successfully", lessonService.getAllByCourse(foundCourse.get()))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find course with id = " + id, "")
            );
        }
    }
    @PostMapping("/note/{id}")
    public ResponseEntity<?> addNoteToLesson(@PathVariable("id") Long id, @RequestBody NoteDTO note) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Add note successfully", lessonService.addNoteToLesson(foundLesson.get(), note))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
    @GetMapping("/notes/{id}")
    public ResponseEntity<?> getAllNotesByLesson(@PathVariable("id") Long id) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query successfully", lessonService.getAllNotesByLesson(foundLesson.get()))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNoteFromLesson(@PathVariable("id") Long id) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            lessonService.deleteNoteInLesson(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete note successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
    @PutMapping("/note/{id}")
    public ResponseEntity<?> updateNoteInLesson(@PathVariable("id") Long id, @RequestBody NoteDTO note) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        if (foundLesson.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update note successfully", lessonService.updateNoteInLesson(foundLesson.get(), note))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
            );
        }
    }
}
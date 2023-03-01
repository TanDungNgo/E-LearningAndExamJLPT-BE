package com.example.ElearningAndExamJNPT.controller;

import com.example.ElearningAndExamJNPT.dto.LessonDTO;
import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.Lesson;
import com.example.ElearningAndExamJNPT.service.impl.LessonServiceImpl;
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
    @GetMapping
    public ResponseEntity<ResponseObject> getAllLesson() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", lessonService.getAll())
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody LessonDTO lesson) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert lesson successfully", lessonService.save(lesson))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Lesson> foundLesson = lessonService.getById(id);
        return foundLesson.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query lesson successfully", foundLesson)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find lesson with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes= "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody Lesson newLesson) {
        Lesson updatedLesson = lessonService.getById(id)
                .map(lesson -> {
                    lesson.setName(newLesson.getName());
                    lesson.setDescription(newLesson.getDescription());
                    lesson.setUrlVideo(newLesson.getUrlVideo());
                    lesson.setRate(newLesson.getRate());
                    return lessonService.save(lesson);
                }).orElseGet(()->{
                    newLesson.setId(id);
                    return lessonService.save(newLesson);
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
}

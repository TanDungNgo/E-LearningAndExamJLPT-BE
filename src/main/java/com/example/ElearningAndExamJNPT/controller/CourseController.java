package com.example.ElearningAndExamJNPT.controller;

import com.example.ElearningAndExamJNPT.dto.CourseDTO;
import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.Course;
import com.example.ElearningAndExamJNPT.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", courseService.getAll())
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseObject> getCoursesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Course> courses = courseService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query successfully", courses)
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setLevel(courseDTO.getLevel());
        course.setBanner(courseDTO.getBanner());
        course.setRate(courseDTO.getRate());
        course.setPrice(courseDTO.getPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert course successfully", courseService.save(course))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findByID(@PathVariable("id") Long id) {
        Optional<Course> foundCourse = courseService.getById(id);
        return foundCourse.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query course successfully", foundCourse)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find course with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody CourseDTO newCourse) {
        Course updatedCourse = courseService.getById(id)
                .map(course -> {
                    course.setName(newCourse.getName());
                    course.setDescription(newCourse.getDescription());
                    course.setLevel(newCourse.getLevel());
                    course.setBanner(newCourse.getBanner());
                    course.setRate(newCourse.getRate());
                    course.setPrice(newCourse.getPrice());
                    return courseService.update(course);
                }).orElseGet(() -> {
                    Course course = new Course();
                    course.setName(newCourse.getName());
                    course.setDescription(newCourse.getDescription());
                    course.setLevel(newCourse.getLevel());
                    course.setBanner(newCourse.getBanner());
                    course.setRate(newCourse.getRate());
                    course.setPrice(newCourse.getPrice());
                    course.setId(id);
                    return courseService.save(course);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update course successfully", updatedCourse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
        Optional<Course> foundCourse = courseService.getById(id);
        if (foundCourse.isPresent()) {
            courseService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete course successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find course with id = " + id, "")
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("query") String query) {
        return ResponseEntity.ok(courseService.searchCourses(query));
    }
}

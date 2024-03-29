package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.CourseDTO;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query course successfully", courseService.getAllCourse())
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseObject> getCoursesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query course successfully", courseService.findAll(pageable))
        );
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseObject> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setLevel(courseDTO.getLevel());
        course.setType(courseDTO.getType());
        course.setBanner(courseDTO.getBanner());
        course.setPrice(courseDTO.getPrice());
        course.setDuration(courseDTO.getDuration());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert course successfully", courseService.save(course))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCourseByID(@PathVariable("id") Long id) {
        Optional<Course> foundCourse = courseService.getById(id);
        return foundCourse.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query course successfully", courseService.getCourse(foundCourse.get())
                        )) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find course with id = " + id, "")
                );
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseObject> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDTO newCourse) {
        Course updatedCourse = courseService.getById(id)
                .map(course -> {
                    course.setName(newCourse.getName());
                    course.setDescription(newCourse.getDescription());
                    course.setLevel(newCourse.getLevel());
                    course.setBanner(newCourse.getBanner());
                    course.setPrice(newCourse.getPrice());
                    course.setDuration(newCourse.getDuration());
                    course.setType(newCourse.getType());
                    return courseService.update(course);
                }).orElseGet(() -> {
                    Course course = new Course();
                    course.setName(newCourse.getName());
                    course.setDescription(newCourse.getDescription());
                    course.setLevel(newCourse.getLevel());
                    course.setBanner(newCourse.getBanner());
                    course.setPrice(newCourse.getPrice());
                    course.setDuration(newCourse.getDuration());
                    course.setType(newCourse.getType());
                    course.setId(id);
                    return courseService.save(course);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update course successfully", updatedCourse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCourse(@PathVariable("id") Long id) {
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
    public ResponseEntity<ResponseObject> searchCourses(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "type", required = false, defaultValue = "") String type,
            @RequestParam(value = "level", required = false, defaultValue = "") String level
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Search course successfully", courseService.searchCourses(query, type, level))
        );
    }

    // Endpoint gợi ý các khóa học dựa trên sở thích và lịch sử học tập của người dùng
    @GetMapping("/suggest")
    public ResponseEntity<ResponseObject> getSuggestedCourses() {
        List<Course> suggestedCourses = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Suggested Course for you", courseService.getSuggestedCourses())
        );
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<?> rateCourse(@PathVariable("id") Long id, @RequestParam("rate") double rate) {
        Optional<Course> foundCourse = courseService.getById(id);
        if (foundCourse.isPresent()) {
            Course course = foundCourse.get();
            courseService.rateCourse(course, rate);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Rate course successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find course with id = " + id, "")
            );
        }
    }

    @GetMapping("/my-courses")
    public ResponseEntity<ResponseObject> getMyCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query course successfully", courseService.getMyCourse())
        );
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query course successfully", courseService.getPopularCourses())
        );
    }
    @GetMapping("/new")
    public ResponseEntity<?> getNewCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query course successfully", courseService.getNewCourses())
        );
    }
}

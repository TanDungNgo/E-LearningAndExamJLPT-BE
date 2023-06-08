package com.example.ElearningAndExamJLPT.controller;

import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.Level;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.service.impl.CourseServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.LessonServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LessonServiceImpl lessonService;

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseObject> getStatistics() {
        int userCount = userService.getAll().size();
        int courseCount = courseService.getAll().size();
        int lessonCount = lessonService.getAll().size();
        int studentCount = userService.findByStudent().size();
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("totalUsers", userCount);
        statistics.put("totalCourses", courseCount);
        statistics.put("totalLessons", lessonCount);
        statistics.put("totalStudents", studentCount);

        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Get statistics successfully", statistics)
        );
    }
    @GetMapping("/coursesByLevel")
    public ResponseEntity<ResponseObject> getCoursesByLevel() {
        Map<Level, Long> coursesByLevel = courseService.getCountCoursesByLevel();
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Get courses by level successfully", coursesByLevel)
        );
    }
    @GetMapping("/accountsByRole")
    public ResponseEntity<ResponseObject> getAccountsByRole() {
        int studentCount = userService.findByStudent().size();
        int teacherCount = userService.findByTeacher().size();
        Map<String, Long> accountsByRole = new HashMap<>();
        accountsByRole.put("student", (long) studentCount);
        accountsByRole.put("teacher", (long) teacherCount);
        return ResponseEntity.ok().body(
                new ResponseObject("ok", "Get accounts by role successfully", accountsByRole)
        );
    }

}

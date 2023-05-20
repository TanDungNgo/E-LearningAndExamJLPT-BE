package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseCourse;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(Course entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return courseRepository.save(entity);
    }

    @Override
    public Course update(Course entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findByUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return courseRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
    @Override
    public List<Course> searchCourses(String query) {
        return courseRepository.searchCourses(query);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<ResponseCourse> getAllCourse() {
        List<ResponseCourse> courses = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        for (Course course : courseList) {
            ResponseCourse responseCourse = new ResponseCourse();
            responseCourse.setId(course.getId());
            responseCourse.setName(course.getName());
            responseCourse.setBanner(course.getBanner());
            responseCourse.setPrice(course.getPrice());
            responseCourse.setDescription(course.getDescription());
            responseCourse.setDuration(course.getDuration());
            responseCourse.setLevel(course.getLevel());
            responseCourse.setType(course.getType());
            responseCourse.setTeacherName(course.getCreatedBy().getUsername());
            responseCourse.setTeacherAvatar(course.getCreatedBy().getAvatar());
            courses.add(responseCourse);
        }
        return courses;
    }
}

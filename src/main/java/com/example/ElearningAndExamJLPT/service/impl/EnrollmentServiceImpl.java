package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.IEnrollmentRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {
    @Autowired
    private IEnrollmentRepository enrollmentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public List<Enrollment> getAll() {
        return null;
    }

    @Override
    public Optional<Enrollment> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Enrollment save(Enrollment entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setStudentId(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setRegistrationDate(now);
        return enrollmentRepository.save(entity);
    }

    @Override
    public Enrollment update(Enrollment entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public boolean existsByStudentIdAndCourseId(Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        Course course = courseRepository.findById(courseId).get();
        return enrollmentRepository.existsByStudentIdAndCourseId(currentUser, course);
    }
    @Override
    public List<User> getStudentsByCourse(Course course) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course);
        List<User> students = enrollments.stream()
                .map(Enrollment::getStudentId)
                .collect(Collectors.toList());
        return students;
    }

}

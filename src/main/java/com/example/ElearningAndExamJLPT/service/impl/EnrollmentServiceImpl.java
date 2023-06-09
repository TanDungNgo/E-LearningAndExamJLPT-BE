package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseCourse;
import com.example.ElearningAndExamJLPT.dto.response.ResponseLesson;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.entity.UserLesson;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.IEnrollmentRepository;
import com.example.ElearningAndExamJLPT.repository.IUserLessonRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {
    @Autowired
    private IEnrollmentRepository enrollmentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserLessonRepository userLessonRepository;

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

    @Override
    public List<ResponseCourse> getCoursesByStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        List<Course> courses = enrollments.stream()
                .map(Enrollment::getCourseId)
                .collect(Collectors.toList());
        List<ResponseCourse> responseCourses = new ArrayList<>();
        for (Course course : courses) {
            ResponseCourse responseCourse = new ResponseCourse();
            responseCourse.setId(course.getId());
            responseCourse.setName(course.getName());
            responseCourse.setBanner(course.getBanner());
            responseCourse.setPrice(course.getPrice());
            responseCourse.setDescription(course.getDescription());
            responseCourse.setDuration(course.getDuration());
            responseCourse.setLevel(course.getLevel());
            responseCourse.setType(course.getType());
            responseCourse.setRate(course.getRate());
            responseCourse.setTeacherName(course.getCreatedBy().getFirstname());
            responseCourse.setTeacherAvatar(course.getCreatedBy().getAvatar());
            List<ResponseLesson> lessons = new ArrayList<>();
            for (Lesson lesson : course.getLessons()) {
                ResponseLesson responseLesson = new ResponseLesson();
                responseLesson.setId(lesson.getId());
                responseLesson.setName(lesson.getName());
                responseLesson.setUrlVideo(lesson.getUrlVideo());
                responseLesson.setRate(lesson.getRate());
                lessons.add(responseLesson);
            }
            responseCourse.setLessons(lessons);
            //
            List<Enrollment> enroll = enrollmentRepository.findByCourseId(course);
            List<User> students = enroll.stream()
                    .map(Enrollment::getStudentId)
                    .collect(Collectors.toList());
            responseCourse.setNumberOfStudent(students.size());
            //
            responseCourses.add(responseCourse);
        }
        return responseCourses;
    }

    @Override
    public Enrollment enrollCourse(Long courseId, Enrollment enrollment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        enrollment.setStudentId(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        enrollment.setRegistrationDate(now);
        Course course = courseRepository.findById(courseId).get();
        List<Lesson> lessons = course.getLessons();
        boolean isFirstLesson = true;
        for (Lesson lesson : lessons) {
            UserLesson userLesson = new UserLesson();
            userLesson.setUser(enrollment.getStudentId());
            userLesson.setLesson(lesson);
            userLesson.setCompleted(isFirstLesson);
            userLessonRepository.save(userLesson);
            isFirstLesson = false;
        }
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Map<String, List<Enrollment>> getMonthlyStudentCount() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Map<String, List<Enrollment>> enrollmentsByMonth = new HashMap<>();

        for (Enrollment enrollment : enrollments) {
            String month = enrollment.getRegistrationDate().getYear() + "-" + enrollment.getRegistrationDate().getMonthValue();
            List<Enrollment> enrollmentsOfMonth = enrollmentsByMonth.getOrDefault(month, new ArrayList<>());
            enrollmentsOfMonth.add(enrollment);
            enrollmentsByMonth.put(month, enrollmentsOfMonth);
        }
        return enrollmentsByMonth;
    }

}

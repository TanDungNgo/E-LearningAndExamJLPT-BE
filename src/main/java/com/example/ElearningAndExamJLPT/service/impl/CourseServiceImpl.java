package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseCourse;
import com.example.ElearningAndExamJLPT.dto.response.ResponseLesson;
import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.CourseRating;
import com.example.ElearningAndExamJLPT.entity.Enrollment;
import com.example.ElearningAndExamJLPT.entity.Lesson;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.repository.ICourseRatingRepository;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.IEnrollmentRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICourseRatingRepository courseRatingRepository;
    @Autowired
    private IEnrollmentRepository enrollmentRepository;

    @Override
    public List<Course> getAll() {
        return courseRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findCourseByDeletedFalseAndId(id);
    }

    @Override
    public Course save(Course entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return courseRepository.save(entity);
    }

    @Override
    public Course update(Course entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return courseRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
//        courseRepository.deleteById(id);
        Optional<Course> course = courseRepository.findCourseByDeletedFalseAndId(id);
        if (course.isPresent()) {
            course.get().setDeleted(true);
            courseRepository.save(course.get());
        }
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
        List<Course> courseList = courseRepository.findAllByDeletedFalse();
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
            List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course);
            List<User> students = enrollments.stream()
                    .map(Enrollment::getStudentId)
                    .collect(Collectors.toList());
            responseCourse.setNumberOfStudent(students.size());
            courses.add(responseCourse);
        }
        return courses;
    }

    @Override
    public ResponseCourse getCourse(Course course) {
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
        //
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course);
        List<User> students = enrollments.stream()
                .map(Enrollment::getStudentId)
                .collect(Collectors.toList());
        responseCourse.setNumberOfStudent(students.size());
        //
        List<ResponseLesson> lessons = new ArrayList<>();
        for (Lesson lesson : course.getLessons()) {
            ResponseLesson responseLesson = new ResponseLesson();
            responseLesson.setId(lesson.getId());
            responseLesson.setName(lesson.getName());
            responseLesson.setUrlVideo(lesson.getUrlVideo());
            responseLesson.setRate(lesson.getRate());
            lessons.add(responseLesson);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                User currentUser = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
                if (enrollmentRepository.existsByStudentIdAndCourseId(currentUser, course))
                    responseCourse.setLessons(lessons);
            }
        } catch (Exception e) {
            responseCourse.setLessons(Collections.emptyList());
        }
        return responseCourse;
    }

    @Override
    public List<ResponseCourse> getSuggestedCourses() {
        List<ResponseCourse> suggestedCourses = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByDeletedFalse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get().getId();
        // Lấy thông tin sở thích và lịch sử học tập của người dùng từ cơ sở dữ liệu hoặc hệ thống lưu trữ tương ứng
        List<String> userPreferences = getUserPreferences(userId);
        List<Course> userHistory = getUserHistory(userId);

        // Gợi ý khóa học dựa trên các yếu tố như chủ đề, cấp độ, ngôn ngữ, độ phổ biến, v.v.
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
            List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course);
            List<User> students = enrollments.stream()
                    .map(Enrollment::getStudentId)
                    .collect(Collectors.toList());
            responseCourse.setNumberOfStudent(students.size());
            // Gợi ý khóa học cùng chủ đề
            if (userPreferences.contains(course.getType())) {
                suggestedCourses.add(responseCourse);
            }
            // Gợi ý khóa học cùng cấp độ
            else if (userPreferences.contains(course.getLevel().toString())) {
                suggestedCourses.add(responseCourse);
            }
            // Gợi ý khóa học dựa trên độ phổ biến (ví dụ: top 10 khóa học được xem nhiều nhất)
            else if (userHistory.contains(course) && suggestedCourses.size() < 10) {
                suggestedCourses.add(responseCourse);
            }
        }
        return suggestedCourses;
    }

    @Override
    public void rateCourse(Course course, double rating) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        CourseRating courseRating = new CourseRating();
        courseRating.setCourse(course);
        courseRating.setUser(user);
        courseRating.setRate(rating);
        courseRatingRepository.save(courseRating);
        List<CourseRating> courseRatings = courseRatingRepository.findByCourse(course);
        double rate = 0;
        for (CourseRating c : courseRatings) {
            rate += c.getRate();
        }
        rate = rate / courseRatings.size();
        course.setRate((double) Math.round(rate * 10.0) / 10.0);
        courseRepository.save(course);
    }

    @Override
    public List<ResponseCourse> getTopCourses() {
        return null;
    }

    @Override
    public List<ResponseCourse> getMyCourse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get();
        List<Course> courses = courseRepository.findAllByDeletedFalseAndCreatedBy(user);
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
            List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course);
            List<User> students = enrollments.stream()
                    .map(Enrollment::getStudentId)
                    .collect(Collectors.toList());
            responseCourse.setNumberOfStudent(students.size());
            //
            responseCourses.add(responseCourse);
        }
        return responseCourses;
    }

    // Hàm giả định lấy thông tin sở thích của người dùng từ cơ sở dữ liệu hoặc hệ thống lưu trữ tương ứng
    private List<String> getUserPreferences(Long userId) {
        // Logic lấy thông tin sở thích từ cơ sở dữ liệu hoặc hệ thống lưu trữ
        List<String> preferences = new ArrayList<>();
        preferences.add("N3");
        preferences.add("N2");
        return preferences; // Trả về danh sách sở thích của người dùng
    }

    private List<Course> getUserHistory(Long userId) {
        // Logic to retrieve user's learning history from the database or storage system
        // ...

        return Collections.emptyList(); // Return the user's learning history
    }
}

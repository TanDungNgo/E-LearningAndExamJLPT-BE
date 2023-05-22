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
import java.util.Collections;
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
            responseCourse.setRate(course.getRate());
            responseCourse.setTeacherName(course.getCreatedBy().getFirstname());
            responseCourse.setTeacherAvatar(course.getCreatedBy().getAvatar());
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
        return responseCourse;
    }

    @Override
    public List<ResponseCourse> getSuggestedCourses() {
        List<ResponseCourse> suggestedCourses = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userRepository.findByUsername(authentication.getName()).get().getId();
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

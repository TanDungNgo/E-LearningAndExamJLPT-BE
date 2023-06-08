package com.example.ElearningAndExamJLPT.service.impl;


import com.example.ElearningAndExamJLPT.entity.Course;
import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.RoleName;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.repository.ICourseRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ICourseRepository courseRepository;

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findUserByDeletedFalseAndUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsernameAndDeletedIsFalse(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndDeletedIsFalse(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findUserByDeletedFalseAndId(id))
                .orElseThrow(() -> new UsernameNotFoundException("Username by " + id + " can not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findByTeacher() {
        Role role = new Role();
        role.setId(2L);
        role.setName(RoleName.TEACHER);
        return userRepository.findByRolesAndDeletedIsFalse(role);
    }

    @Override
    public List<User> findByStudent() {
        Role role = new Role();
        role.setId(3L);
        role.setName(RoleName.STUDENT);
        return userRepository.findByRolesAndDeletedIsFalse(role);
    }

    @Override
    public List<Map<String, Object>> getTopTeachers() {
        List<Map<String, Object>> topTeachers = new ArrayList<>();
        Role role = new Role();
        role.setId(2L);
        role.setName(RoleName.TEACHER);
        List<User> teachers = userRepository.findByRolesAndDeletedIsFalse(role);
        for (User teacher : teachers) {
            List<Course> courses = courseRepository.findAllByDeletedFalseAndCreatedBy(teacher);
            int count = courses.size();
            Map<String, Object> entry = new HashMap<>();
            entry.put("user", teacher);
            entry.put("courseCount", count);
            topTeachers.add(entry);
        }
        topTeachers.sort(Comparator.comparingInt(entry -> (Integer) entry.get("courseCount")));
        Collections.reverse(topTeachers);
        return topTeachers;
    }



    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
//        userRepository.deleteById(id);
        User user = userRepository.findUserByDeletedFalseAndId(id).get();
        user.setDeleted(true);
        userRepository.save(user);
    }
}

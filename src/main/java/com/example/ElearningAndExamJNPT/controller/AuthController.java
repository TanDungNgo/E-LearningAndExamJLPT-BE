package com.example.ElearningAndExamJNPT.controller;


import com.example.ElearningAndExamJNPT.dto.request.SignInForm;
import com.example.ElearningAndExamJNPT.dto.request.SignUpForm;
import com.example.ElearningAndExamJNPT.dto.response.JwtResponse;
import com.example.ElearningAndExamJNPT.dto.response.ResponMessage;
import com.example.ElearningAndExamJNPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJNPT.entity.User.Role;
import com.example.ElearningAndExamJNPT.entity.User.RoleName;
import com.example.ElearningAndExamJNPT.entity.User.User;
import com.example.ElearningAndExamJNPT.security.jwt.JwtProvider;
import com.example.ElearningAndExamJNPT.security.userprincal.UserPrinciple;
import com.example.ElearningAndExamJNPT.service.impl.RoleServiceImpl;
import com.example.ElearningAndExamJNPT.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponMessage("The username existed! Please try again!"), HttpStatus.CONFLICT);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponMessage("The email existed! Please try again!"), HttpStatus.CONFLICT);
        }
        User user = new User(signUpForm.getFirstname(), signUpForm.getLastname(), signUpForm.getUsername(), signUpForm.getGender(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getAvatar());

        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(adminRole);
                    break;
                case "TEACHER":
                    Role teacherRole = roleService.findByName(RoleName.TEACHER)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(teacherRole);
                    break;
                default:
                    Role studentRole = roleService.findByName(RoleName.STUDENT)
                            .orElseThrow(() -> new RuntimeException("Role not found!"));
                    roles.add(studentRole);
                    break;
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponMessage("Signup success!"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Signin successfully",
                        new JwtResponse(userPrinciple.getId(), token, userPrinciple.getFirstname(), userPrinciple.getLastname(), userPrinciple.getAvatar(), userPrinciple.getAuthorities())
                ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

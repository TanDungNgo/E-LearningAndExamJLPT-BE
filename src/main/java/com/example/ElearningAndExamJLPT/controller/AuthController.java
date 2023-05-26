package com.example.ElearningAndExamJLPT.controller;


import com.example.ElearningAndExamJLPT.dto.request.ChangePasswordRequest;
import com.example.ElearningAndExamJLPT.dto.request.SignInForm;
import com.example.ElearningAndExamJLPT.dto.request.SignUpForm;
import com.example.ElearningAndExamJLPT.dto.request.UpdateProfileRequest;
import com.example.ElearningAndExamJLPT.dto.response.JwtResponse;
import com.example.ElearningAndExamJLPT.dto.response.ResponMessage;
import com.example.ElearningAndExamJLPT.dto.response.ResponseObject;
import com.example.ElearningAndExamJLPT.entity.User.Role;
import com.example.ElearningAndExamJLPT.entity.User.RoleName;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.security.jwt.JwtProvider;
import com.example.ElearningAndExamJLPT.security.userprincal.UserPrinciple;
import com.example.ElearningAndExamJLPT.service.impl.RoleServiceImpl;
import com.example.ElearningAndExamJLPT.service.impl.UserServiceImpl;
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
import java.util.*;

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
        String strGender = String.valueOf(signUpForm.getGender());
        if(strGender == "Male")
        {
            signUpForm.setAvatar("https://i.pinimg.com/736x/04/b2/51/04b2516f33364982853c0bec7cae9fe3.jpg");
        }
        else{
            signUpForm.setAvatar("https://i.pinimg.com/736x/84/fe/b0/84feb03ef83bc2cdacfdcc03a8a3aa69.jpg");
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

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByUsername(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get user info successfully", user)
        );
    }

    @PutMapping("/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        currentUser.getName(), request.getOldPassword()
                )
        );
        // Kiểm tra xác thực thành công
        if (authentication.isAuthenticated()) {
            // Kiểm tra mật khẩu mới và xác nhận mật khẩu
            if (request.getNewPassword().equals(request.getConfirmPassword())) {
                // Mã hóa mật khẩu mới
                String encodedPassword = passwordEncoder.encode(request.getNewPassword());

                // Lưu trữ mật khẩu mới vào cơ sở dữ liệu
                // Tìm người dùng trong cơ sở dữ liệu
                User user = userService.findByUsername(currentUser.getName()).get();

                if (user != null) {
                    // Cập nhật mật khẩu mới cho người dùng
                    user.setPassword(encodedPassword);
                    userService.save(user);

                    return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Password changed successfully.", "")
                    );
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject("Fail", "User not found.", "")
                    );
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("Fail", "New password and confirm password do not match.", "")
                );
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Fail", "Invalid username or password.", "")
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(currentUser.getName()).get();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update profile successfully", "")
        );

    }
}

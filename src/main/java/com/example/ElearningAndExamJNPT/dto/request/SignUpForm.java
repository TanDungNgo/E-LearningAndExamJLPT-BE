package com.example.ElearningAndExamJNPT.dto.request;

import com.example.ElearningAndExamJNPT.entity.User.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpForm {
    private String firstname;
    private String lastname;
    private String username;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private String email;
    private String password;
    private String avatar;
    private Set<String> roles;
}

package com.example.ElearningAndExamJLPT.dto.request;

import com.example.ElearningAndExamJLPT.entity.User.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProfileRequest {
    private String firstname;
    private String lastname;
    private GenderType gender;
    private String email;
    private String avatar;
}

package com.example.ElearningAndExamJLPT.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {
    @NotBlank(message = "OldPassword is mandatory")
    private String oldPassword;
    @NotBlank(message = "NewPassword is mandatory")
    private String newPassword;
    @NotBlank(message = "ConfirmPassword is mandatory")
    private String confirmPassword;
}

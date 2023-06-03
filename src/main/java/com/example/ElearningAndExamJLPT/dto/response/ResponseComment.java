package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseComment {
    private Long id;
    private String content;
    private String createdAt;
    private String createdBy;
    private String avatar;
    private List<ResponseComment> replies;
}

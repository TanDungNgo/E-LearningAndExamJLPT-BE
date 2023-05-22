package com.example.ElearningAndExamJLPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseArticle {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String image;
    private String createdDate;
    private String modifiedDate;
    private String createdBy;
    private String avatarCreatedBy;
}

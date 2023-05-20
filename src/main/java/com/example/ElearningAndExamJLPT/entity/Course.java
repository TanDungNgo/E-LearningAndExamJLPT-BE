package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
@Validated
public class Course extends BaseEntity {
    @NotBlank(message = "Course name is mandatory")
    @Size(min = 3, max = 100)
    private String name;
    @Lob
    @NotBlank(message = "Course description is mandatory")
    private String description;
    @Min(0)
    @Max(5)
    private Double rate;
    private String duration;
    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;
    @NotNull(message = "Course level is mandatory")
    @Enumerated(EnumType.STRING)
    private Level level;
    @NotNull(message = "Course type is mandatory")
    private String type;
    @Lob
    private String banner;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "courseId")
    private List<Enrollment> enrollments;
    @JsonIgnore
    private boolean status = true;
}

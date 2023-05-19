package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vocabularies")
public class Vocabulary extends BaseEntity {
    @NotBlank
    @Size(min = 3, max = 10)
    private String text;
    @NotBlank
    private String meaning;
    @NotBlank
    @Size(min = 3, max = 50)
    private String pronunciation;
    @NotBlank
    @Size(min = 3, max = 50)
    private String spelling;
    @NotBlank
    @Size(min = 3, max = 255)
    private String example;
    @Lob
    private String audio;
    @JsonIgnore
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "VocabularyFolder_id")
    @JsonBackReference
    private VocabularyFolder vocabularyFolder;
}

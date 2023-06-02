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
    private String text;
    @NotBlank
    private String meaning;
    @NotBlank
    private String pronunciation;
    @NotBlank
    private String spelling;
    @NotBlank
    @Lob
    private String example;
    @Lob
    private String audio;
    @JsonIgnore
    private boolean status = true;
    @JsonIgnore
    private boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "VocabularyFolder_id")
    @JsonBackReference
    private VocabularyFolder vocabularyFolder;
}

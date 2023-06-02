package com.example.ElearningAndExamJLPT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vocabulary_folders")
public class VocabularyFolder extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Level level;
    @NotBlank
    @Size(min = 3, max = 50)
    private String title;
    @Formula("(SELECT COUNT(*) FROM vocabularies v WHERE v.vocabulary_folder_id = id)")
    private Integer count;
    @JsonIgnore
    private boolean status = true;
    @JsonIgnore
    private boolean deleted = false;
    @OneToMany(mappedBy = "vocabularyFolder")
    private List<Vocabulary> vocabularies = new ArrayList<>();
}

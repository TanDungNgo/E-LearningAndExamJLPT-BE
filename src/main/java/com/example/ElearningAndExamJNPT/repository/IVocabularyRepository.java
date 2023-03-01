package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVocabularyRepository extends JpaRepository<Vocabulary,Long> {
    @Query("SELECT v FROM Vocabulary v WHERE " +
            "v.text LIKE CONCAT('%',:query, '%')")
    List<Vocabulary> searchVocabularies(String query);
}

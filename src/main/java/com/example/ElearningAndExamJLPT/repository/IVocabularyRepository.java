package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVocabularyRepository extends JpaRepository<Vocabulary,Long> {
    @Query("SELECT v FROM Vocabulary v WHERE " +
            "v.text LIKE CONCAT('%',:query, '%')"+
            "Or v.meaning LIKE CONCAT('%', :query, '%')"+
            "AND v.deleted = false")
    List<Vocabulary> searchVocabularies(String query);
    List<Vocabulary> findAllByDeletedFalse();
    Optional<Vocabulary> findVocabularyByDeletedFalseAndId(Long id);
}

package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.Level;
import com.example.ElearningAndExamJLPT.entity.VocabularyFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVocabularyFolderRepository extends JpaRepository<VocabularyFolder, Long> {
    @Query("SELECT f FROM VocabularyFolder f WHERE " +
            "f.title LIKE CONCAT('%',:query, '%') " +
            "Or f.level = :query " +
            "AND f.deleted = false")
    List<VocabularyFolder> searchVocabularyFolders(String query);
    List<VocabularyFolder> findAllByDeletedFalse();
    Optional<VocabularyFolder> findVocabularyFolderByDeletedFalseAndId(Long id);
}

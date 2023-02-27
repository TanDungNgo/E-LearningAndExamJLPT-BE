package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGrammarRepository extends JpaRepository<Grammar,Long> {
    @Query("SELECT g FROM Grammar g WHERE " +
            "g.text LIKE CONCAT('%',:query, '%')" +
            "Or g.means LIKE CONCAT('%', :query, '%')")
    List<Grammar> searchGrammars(String query);
}

package com.example.ElearningAndExamJNPT.repository;

import com.example.ElearningAndExamJNPT.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrammarRepository extends JpaRepository<Grammar,Long> {
}

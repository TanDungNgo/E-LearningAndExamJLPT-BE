package com.example.ElearningAndExamJLPT.repository;

import com.example.ElearningAndExamJLPT.entity.ListeningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IListeningQuestion extends JpaRepository<ListeningQuestion, Long> {
}

package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseExam;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.entity.*;
import com.example.ElearningAndExamJLPT.repository.IExamRepository;
import com.example.ElearningAndExamJLPT.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements IExamService {
    @Autowired
    private IExamRepository examRepository;

    @Override
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam save(Exam entity) {
        return null;
    }

    @Override
    public Exam update(Exam entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ResponseExam getExam(Exam exam) {
        ResponseExam responseExam = new ResponseExam();
        List<ResponseQuestion> listLanguageKnowledgeQuestions = new ArrayList<>();
        List<ResponseQuestion> listReadingQuestions = new ArrayList<>();
        List<ResponseQuestion> listListeningQuestions = new ArrayList<>();
        int durationLanguageKnowledgeQuestion = 0;
        int durationReadingQuestion = 0;
        int durationListeningQuestion = 0;
        responseExam.setId(exam.getId());
        responseExam.setName(exam.getName());
        responseExam.setLevel(exam.getLevel());

        for (LanguageKnowledgeQuestion question : exam.getLanguageKnowledgeQuestions()) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(question.getId());
            responseQuestion.setText(question.getText());
            responseQuestion.setOption1(question.getOption1());
            responseQuestion.setOption2(question.getOption2());
            responseQuestion.setOption3(question.getOption3());
            responseQuestion.setOption4(question.getOption4());
            listLanguageKnowledgeQuestions.add(responseQuestion);
            durationLanguageKnowledgeQuestion += 1;
        }
        for (ReadingQuestion question : exam.getReadingQuestions()) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(question.getId());
            responseQuestion.setText(question.getText());
            responseQuestion.setImage(question.getImage());
            responseQuestion.setOption1(question.getOption1());
            responseQuestion.setOption2(question.getOption2());
            responseQuestion.setOption3(question.getOption3());
            responseQuestion.setOption4(question.getOption4());
            listReadingQuestions.add(responseQuestion);
            durationReadingQuestion += 5;
        }
        for (ListeningQuestion question : exam.getListeningQuestions()) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(question.getId());
            responseQuestion.setText(question.getText());
            responseQuestion.setAudioFile(question.getAudioFile());
            responseQuestion.setImage(question.getImage());
            responseQuestion.setOption1(question.getOption1());
            responseQuestion.setOption2(question.getOption2());
            responseQuestion.setOption3(question.getOption3());
            responseQuestion.setOption4(question.getOption4());
            listListeningQuestions.add(responseQuestion);
            durationListeningQuestion += 2;
        }
        responseExam.setDurationLanguageKnowledge(durationLanguageKnowledgeQuestion);
        responseExam.setDurationReading(durationReadingQuestion);
        responseExam.setDurationListening(durationListeningQuestion);
        responseExam.setLanguageKnowledgeQuestions(listLanguageKnowledgeQuestions);
        responseExam.setReadingQuestions(listReadingQuestions);
        responseExam.setListeningQuestions(listListeningQuestions);
        return responseExam;
    }

    @Override
    public ResponseExam getRandomExam(String level) {
        List<Exam> listExam = new ArrayList<>();
        switch (level) {
            case "N5":
                listExam = examRepository.findAllByLevel(Level.N5);
                break;
            case "N4":
                listExam = examRepository.findAllByLevel(Level.N4);
                break;
            case "N3":
                listExam = examRepository.findAllByLevel(Level.N3);
                break;
            case "N2":
                listExam = examRepository.findAllByLevel(Level.N2);
                break;
            case "N1":
                listExam = examRepository.findAllByLevel(Level.N1);
                break;
        }
        if (listExam.size() > 0) {
            int randomIndex = (int) (Math.random() * listExam.size());
            return getExam(listExam.get(randomIndex));
        }
        return null;
    }
}

package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseExam;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.entity.Exam;
import com.example.ElearningAndExamJLPT.entity.LanguageKnowledgeQuestion;
import com.example.ElearningAndExamJLPT.entity.ListeningQuestion;
import com.example.ElearningAndExamJLPT.entity.ReadingQuestion;
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
        return null;
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
        responseExam.setId(exam.getId());
        responseExam.setName(exam.getName());
        responseExam.setLevel(exam.getLevel());
//        responseExam.setDuration(exam.getDuration());
        for (LanguageKnowledgeQuestion question : exam.getLanguageKnowledgeQuestions()) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(question.getId());
            responseQuestion.setText(question.getText());
            responseQuestion.setOption1(question.getOption1());
            responseQuestion.setOption2(question.getOption2());
            responseQuestion.setOption3(question.getOption3());
            responseQuestion.setOption4(question.getOption4());
            listLanguageKnowledgeQuestions.add(responseQuestion);
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
        }
        responseExam.setLanguageKnowledgeQuestions(listLanguageKnowledgeQuestions);
        responseExam.setReadingQuestions(listReadingQuestions);
        responseExam.setListeningQuestions(listListeningQuestions);
        return responseExam;
    }
}

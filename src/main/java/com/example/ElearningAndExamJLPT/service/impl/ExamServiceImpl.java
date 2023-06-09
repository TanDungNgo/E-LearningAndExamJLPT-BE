package com.example.ElearningAndExamJLPT.service.impl;

import com.example.ElearningAndExamJLPT.dto.response.ResponseExam;
import com.example.ElearningAndExamJLPT.dto.response.ResponseExamResult;
import com.example.ElearningAndExamJLPT.dto.response.ResponseQuestion;
import com.example.ElearningAndExamJLPT.dto.response.ResponseUserExam;
import com.example.ElearningAndExamJLPT.entity.*;
import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.repository.IExamRepository;
import com.example.ElearningAndExamJLPT.repository.IExamResultRepository;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import com.example.ElearningAndExamJLPT.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExamServiceImpl implements IExamService {
    @Autowired
    private IExamRepository examRepository;
    @Autowired
    private IExamResultRepository examResultRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<Exam> getAll() {
        return examRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Exam> getById(Long id) {
        return examRepository.findExamByDeletedFalseAndId(id);
    }

    @Override
    public Exam save(Exam entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setCreatedDate(now);
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return examRepository.save(entity);
    }

    @Override
    public Exam update(Exam entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        entity.setModifiedBy(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        entity.setModifiedDate(now);
        return examRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Exam> exam = examRepository.findExamByDeletedFalseAndId(id);
        if(exam.isPresent())
        {
            exam.get().setDeleted(true);
            examRepository.save(exam.get());
        }
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
            responseQuestion.setTitle(question.getTitle());
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
            responseQuestion.setTitle(question.getTitle());
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
            responseQuestion.setTitle(question.getTitle());
            responseQuestion.setAudioFile(question.getAudioFile());
            responseQuestion.setImage(question.getImage());
            responseQuestion.setOption1(question.getOption1());
            responseQuestion.setOption2(question.getOption2());
            responseQuestion.setOption3(question.getOption3());
            responseQuestion.setOption4(question.getOption4());
            listListeningQuestions.add(responseQuestion);
            durationListeningQuestion += 2;
        }
        responseExam.setDurationLanguageKnowledge(durationLanguageKnowledgeQuestion * 60);
        responseExam.setDurationReading(durationReadingQuestion * 60);
        responseExam.setDurationListening(durationListeningQuestion * 60);
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
                listExam = examRepository.findAllByDeletedFalseAndLevel(Level.N5);
                break;
            case "N4":
                listExam = examRepository.findAllByDeletedFalseAndLevel(Level.N4);
                break;
            case "N3":
                listExam = examRepository.findAllByDeletedFalseAndLevel(Level.N3);
                break;
            case "N2":
                listExam = examRepository.findAllByDeletedFalseAndLevel(Level.N2);
                break;
            case "N1":
                listExam = examRepository.findAllByDeletedFalseAndLevel(Level.N1);
                break;
        }
        if (listExam.size() > 0) {
            int randomIndex = (int) (Math.random() * listExam.size());
            return getExam(listExam.get(randomIndex));
        }
        return null;
    }

    @Override
    public ResponseExamResult submitExam(int[] answers, Exam foundExam) {
        List<LanguageKnowledgeQuestion> languageKnowledgeQuestions = foundExam.getLanguageKnowledgeQuestions();
        int[] correctAnswerLanguageKnowledgeQuestion = new int[languageKnowledgeQuestions.size()];

        List<ReadingQuestion> readingQuestions = foundExam.getReadingQuestions();
        int[] correctAnswerReadingQuestion = new int[readingQuestions.size()];

        List<ListeningQuestion> listeningQuestions = foundExam.getListeningQuestions();
        int[] correctAnswerListeningQuestion = new int[listeningQuestions.size()];
        List<ResponseQuestion> languageKnowledge = new ArrayList<>();
        List<ResponseQuestion> reading = new ArrayList<>();
        List<ResponseQuestion> listening = new ArrayList<>();
        for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(languageKnowledgeQuestions.get(i).getId());
            responseQuestion.setTitle(languageKnowledgeQuestions.get(i).getTitle());
            responseQuestion.setText(languageKnowledgeQuestions.get(i).getText());
            responseQuestion.setOption1(languageKnowledgeQuestions.get(i).getOption1());
            responseQuestion.setOption2(languageKnowledgeQuestions.get(i).getOption2());
            responseQuestion.setOption3(languageKnowledgeQuestions.get(i).getOption3());
            responseQuestion.setOption4(languageKnowledgeQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(languageKnowledgeQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i]);
            responseQuestion.setExplanation(languageKnowledgeQuestions.get(i).getExplanation());
            languageKnowledge.add(responseQuestion);
            correctAnswerLanguageKnowledgeQuestion[i] = languageKnowledgeQuestions.get(i).getCorrectAnswer();

        }
        for (int i = 0; i < readingQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(readingQuestions.get(i).getId());
            responseQuestion.setTitle(readingQuestions.get(i).getTitle());
            responseQuestion.setText(readingQuestions.get(i).getText());
            responseQuestion.setImage(readingQuestions.get(i).getImage());
            responseQuestion.setOption1(readingQuestions.get(i).getOption1());
            responseQuestion.setOption2(readingQuestions.get(i).getOption2());
            responseQuestion.setOption3(readingQuestions.get(i).getOption3());
            responseQuestion.setOption4(readingQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(readingQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size()]);
            responseQuestion.setExplanation(readingQuestions.get(i).getExplanation());
            reading.add(responseQuestion);
            correctAnswerReadingQuestion[i] = readingQuestions.get(i).getCorrectAnswer();
        }
        for (int i = 0; i < listeningQuestions.size(); i++) {
            ResponseQuestion responseQuestion = new ResponseQuestion();
            responseQuestion.setId(listeningQuestions.get(i).getId());
            responseQuestion.setTitle(listeningQuestions.get(i).getTitle());
            responseQuestion.setText(listeningQuestions.get(i).getText());
            responseQuestion.setImage(listeningQuestions.get(i).getImage());
            responseQuestion.setAudioFile(listeningQuestions.get(i).getAudioFile());
            responseQuestion.setOption1(listeningQuestions.get(i).getOption1());
            responseQuestion.setOption2(listeningQuestions.get(i).getOption2());
            responseQuestion.setOption3(listeningQuestions.get(i).getOption3());
            responseQuestion.setOption4(listeningQuestions.get(i).getOption4());
            responseQuestion.setCorrectAnswer(listeningQuestions.get(i).getCorrectAnswer());
            responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()]);
            responseQuestion.setExplanation(listeningQuestions.get(i).getExplanation());
            listening.add(responseQuestion);
            correctAnswerListeningQuestion[i] = listeningQuestions.get(i).getCorrectAnswer();
        }
        int totalCorrectLanguageKnowledgeQuestion = 0;
        for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
            if (answers[i] == correctAnswerLanguageKnowledgeQuestion[i]) {
                totalCorrectLanguageKnowledgeQuestion++;
            }
        }
        int totalCorrectReadingQuestion = 0;
        for (int i = 0; i < readingQuestions.size(); i++) {
            if (answers[i + languageKnowledgeQuestions.size()] == correctAnswerReadingQuestion[i]) {
                totalCorrectReadingQuestion++;
            }
        }
        int totalCorrectListeningQuestion = 0;
        for (int i = 0; i < listeningQuestions.size(); i++) {
            if (answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()] == correctAnswerListeningQuestion[i]) {
                totalCorrectListeningQuestion++;
            }
        }

        int totalCorrectAnswer = totalCorrectLanguageKnowledgeQuestion + totalCorrectReadingQuestion + totalCorrectListeningQuestion;

        double percentage = (double) totalCorrectAnswer / answers.length * 100;

        ResponseExamResult responseExamResult = new ResponseExamResult();
        responseExamResult.setExamName(foundExam.getName());
        responseExamResult.setTotalLanguageKnowledgeQuestion(languageKnowledgeQuestions.size());
        responseExamResult.setCorrectAnswerLanguageKnowledgeQuestion(totalCorrectLanguageKnowledgeQuestion);
        responseExamResult.setTotalReadingQuestion(readingQuestions.size());
        responseExamResult.setCorrectAnswerReadingQuestion(totalCorrectReadingQuestion);
        responseExamResult.setTotalListeningQuestion(listeningQuestions.size());
        responseExamResult.setCorrectAnswerListeningQuestion(totalCorrectListeningQuestion);
        responseExamResult.setCorrectAnswer(totalCorrectAnswer);
        int totalQuestion = languageKnowledgeQuestions.size() + readingQuestions.size() + listeningQuestions.size();
        responseExamResult.setTotalQuestion(totalQuestion);
        responseExamResult.setPercentage((int) Math.round(percentage));
        responseExamResult.setExamDate(String.valueOf(LocalDateTime.now()));
        responseExamResult.setLanguageKnowledgeQuestions(languageKnowledge);
        responseExamResult.setReadingQuestions(reading);
        responseExamResult.setListeningQuestions(listening);
        if (percentage >= 50) {
            responseExamResult.setStatus("Pass");
        } else {
            responseExamResult.setStatus("Fail");
        }
        ExamResult examResult = new ExamResult();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LocalDateTime now = LocalDateTime.now();
        examResult.setExamId(foundExam);
        examResult.setStudentId(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
        examResult.setExamDate(now);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < answers.length; i++) {
            stringBuilder.append(answers[i]);
            if (i != answers.length - 1) {
                stringBuilder.append(", ");
            }
        }
        String arrayString = stringBuilder.toString();
        examResult.setAnswers(arrayString);
        examResult.setScore((double) Math.round(percentage));
        if (percentage >= 50) {
            examResult.setStatus("Pass");
        } else {
            examResult.setStatus("Fail");
        }
        examResultRepository.save(examResult);
        return responseExamResult;
    }

    @Override
    public List<ExamResult> getAllExamResult() {
        return examResultRepository.findAll();
    }

    @Override
    public List<ExamResult> getAllExamResultByStudentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return examResultRepository.findAllByStudentId(userRepository.findUserByDeletedFalseAndUsername(authentication.getName()).get());
    }

    @Override
    public ResponseExamResult getExamResultById(Long id) {
        Optional<ExamResult> examResult = examResultRepository.findById(id);
        int[] answers = Arrays.stream(examResult.get().getAnswers().split(", ")).mapToInt(Integer::parseInt).toArray();
        if (examResult.isPresent()) {
            ResponseExamResult responseExamResult = new ResponseExamResult();
            responseExamResult.setExamName(examResult.get().getExamId().getName());
            responseExamResult.setTotalLanguageKnowledgeQuestion(examResult.get().getExamId().getLanguageKnowledgeQuestions().size());
            responseExamResult.setTotalReadingQuestion(examResult.get().getExamId().getReadingQuestions().size());
            responseExamResult.setTotalListeningQuestion(examResult.get().getExamId().getListeningQuestions().size());
            responseExamResult.setTotalQuestion(examResult.get().getExamId().getLanguageKnowledgeQuestions().size() + examResult.get().getExamId().getReadingQuestions().size() + examResult.get().getExamId().getListeningQuestions().size());
            responseExamResult.setPercentage((int) Math.round(examResult.get().getScore()));
            responseExamResult.setExamDate(String.valueOf(examResult.get().getExamDate()));
            responseExamResult.setStatus(examResult.get().getStatus());

            List<LanguageKnowledgeQuestion> languageKnowledgeQuestions = examResult.get().getExamId().getLanguageKnowledgeQuestions();
            int[] correctAnswerLanguageKnowledgeQuestion = new int[languageKnowledgeQuestions.size()];

            List<ReadingQuestion> readingQuestions = examResult.get().getExamId().getReadingQuestions();
            int[] correctAnswerReadingQuestion = new int[readingQuestions.size()];

            List<ListeningQuestion> listeningQuestions = examResult.get().getExamId().getListeningQuestions();
            int[] correctAnswerListeningQuestion = new int[listeningQuestions.size()];
            List<ResponseQuestion> languageKnowledge = new ArrayList<>();
            List<ResponseQuestion> reading = new ArrayList<>();
            List<ResponseQuestion> listening = new ArrayList<>();
            for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
                ResponseQuestion responseQuestion = new ResponseQuestion();
                responseQuestion.setId(languageKnowledgeQuestions.get(i).getId());
                responseQuestion.setTitle(languageKnowledgeQuestions.get(i).getTitle());
                responseQuestion.setText(languageKnowledgeQuestions.get(i).getText());
                responseQuestion.setOption1(languageKnowledgeQuestions.get(i).getOption1());
                responseQuestion.setOption2(languageKnowledgeQuestions.get(i).getOption2());
                responseQuestion.setOption3(languageKnowledgeQuestions.get(i).getOption3());
                responseQuestion.setOption4(languageKnowledgeQuestions.get(i).getOption4());
                responseQuestion.setCorrectAnswer(languageKnowledgeQuestions.get(i).getCorrectAnswer());
                responseQuestion.setAnswer(answers[i]);
                responseQuestion.setExplanation(languageKnowledgeQuestions.get(i).getExplanation());
                languageKnowledge.add(responseQuestion);
                correctAnswerLanguageKnowledgeQuestion[i] = languageKnowledgeQuestions.get(i).getCorrectAnswer();

            }
            for (int i = 0; i < readingQuestions.size(); i++) {
                ResponseQuestion responseQuestion = new ResponseQuestion();
                responseQuestion.setId(readingQuestions.get(i).getId());
                responseQuestion.setTitle(readingQuestions.get(i).getTitle());
                responseQuestion.setText(readingQuestions.get(i).getText());
                responseQuestion.setImage(readingQuestions.get(i).getImage());
                responseQuestion.setOption1(readingQuestions.get(i).getOption1());
                responseQuestion.setOption2(readingQuestions.get(i).getOption2());
                responseQuestion.setOption3(readingQuestions.get(i).getOption3());
                responseQuestion.setOption4(readingQuestions.get(i).getOption4());
                responseQuestion.setCorrectAnswer(readingQuestions.get(i).getCorrectAnswer());
                responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size()]);
                responseQuestion.setExplanation(readingQuestions.get(i).getExplanation());
                reading.add(responseQuestion);
                correctAnswerReadingQuestion[i] = readingQuestions.get(i).getCorrectAnswer();
            }
            for (int i = 0; i < listeningQuestions.size(); i++) {
                ResponseQuestion responseQuestion = new ResponseQuestion();
                responseQuestion.setId(listeningQuestions.get(i).getId());
                responseQuestion.setTitle(listeningQuestions.get(i).getTitle());
                responseQuestion.setText(listeningQuestions.get(i).getText());
                responseQuestion.setImage(listeningQuestions.get(i).getImage());
                responseQuestion.setAudioFile(listeningQuestions.get(i).getAudioFile());
                responseQuestion.setOption1(listeningQuestions.get(i).getOption1());
                responseQuestion.setOption2(listeningQuestions.get(i).getOption2());
                responseQuestion.setOption3(listeningQuestions.get(i).getOption3());
                responseQuestion.setOption4(listeningQuestions.get(i).getOption4());
                responseQuestion.setCorrectAnswer(listeningQuestions.get(i).getCorrectAnswer());
                responseQuestion.setAnswer(answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()]);
                responseQuestion.setExplanation(listeningQuestions.get(i).getExplanation());
                listening.add(responseQuestion);
                correctAnswerListeningQuestion[i] = listeningQuestions.get(i).getCorrectAnswer();
            }
            int totalCorrectLanguageKnowledgeQuestion = 0;
            for (int i = 0; i < languageKnowledgeQuestions.size(); i++) {
                if (answers[i] == correctAnswerLanguageKnowledgeQuestion[i]) {
                    totalCorrectLanguageKnowledgeQuestion++;
                }
            }
            int totalCorrectReadingQuestion = 0;
            for (int i = 0; i < readingQuestions.size(); i++) {
                if (answers[i + languageKnowledgeQuestions.size()] == correctAnswerReadingQuestion[i]) {
                    totalCorrectReadingQuestion++;
                }
            }
            int totalCorrectListeningQuestion = 0;
            for (int i = 0; i < listeningQuestions.size(); i++) {
                if (answers[i + languageKnowledgeQuestions.size() + readingQuestions.size()] == correctAnswerListeningQuestion[i]) {
                    totalCorrectListeningQuestion++;
                }
            }
            responseExamResult.setCorrectAnswerLanguageKnowledgeQuestion(totalCorrectLanguageKnowledgeQuestion);
            responseExamResult.setTotalReadingQuestion(readingQuestions.size());
            responseExamResult.setCorrectAnswerReadingQuestion(totalCorrectReadingQuestion);
            responseExamResult.setTotalListeningQuestion(listeningQuestions.size());
            responseExamResult.setCorrectAnswerListeningQuestion(totalCorrectListeningQuestion);
            int totalCorrectAnswer = totalCorrectLanguageKnowledgeQuestion + totalCorrectReadingQuestion + totalCorrectListeningQuestion;
            responseExamResult.setCorrectAnswer(totalCorrectAnswer);
            responseExamResult.setLanguageKnowledgeQuestions(languageKnowledge);
            responseExamResult.setReadingQuestions(reading);
            responseExamResult.setListeningQuestions(listening);
            return responseExamResult;
        }
        return null;
    }

    @Override
    public List<ResponseUserExam> getTop3ExamResult() {
        List<ExamResult> examResults = examResultRepository.findAll();
        examResults.sort(Comparator.comparing(ExamResult::getScore).reversed());
        List<ResponseUserExam> top3ExamResults = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ResponseUserExam responseUserExam = new ResponseUserExam();
            responseUserExam.setId(examResults.get(i).getId());
            responseUserExam.setScore(examResults.get(i).getScore());
            responseUserExam.setExamName(examResults.get(i).getExamId().getName());
            responseUserExam.setStudentName(examResults.get(i).getStudentId().getFirstname() + " " + examResults.get(i).getStudentId().getLastname());
            responseUserExam.setAvatar(examResults.get(i).getStudentId().getAvatar());
            responseUserExam.setStatus(examResults.get(i).getStatus());
            responseUserExam.setExamDate(examResults.get(i).getExamDate());
            top3ExamResults.add(responseUserExam);
        }


        return top3ExamResults;
    }

}

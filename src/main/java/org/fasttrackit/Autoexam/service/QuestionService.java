package org.fasttrackit.Autoexam.service;

import org.fasttrackit.Autoexam.Exceptions.EndOfQuestionsException;
import org.fasttrackit.Autoexam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.fasttrackit.Autoexam.model.Question;

import java.util.*;


@Service

public class QuestionService {
    private final QuestionRepository repository;
    private int generatedQuestionsCount = 0;

    @Autowired
    public QuestionService(QuestionFileReader questionFileReader, QuestionRepository repository) {
        this.repository = repository;
        repository.saveAll(questionFileReader.populateWithDataFromFile());
    }


    public List<Question> getAllQuestions(Question question) {
        return repository.findAll();
    }


    public Question getRandomQuestion() {

        Random random = new Random();

        List<Question> questions = repository.findAllByReceivedFalse();

        int questionsSize = questions.size();
        if (generatedQuestionsCount>=26){
            return null;
        }else {

            if (questions.isEmpty()) {

                throw new EndOfQuestionsException("You went through all the questions");

            }

            Question randomQuestion = questions.get(random.nextInt(questionsSize));



            randomQuestion.setReceived(true);
            repository.save(randomQuestion);
            generatedQuestionsCount++;

            return randomQuestion;

        }
    }

    public int getGeneratedQuestionsCount() {
        return generatedQuestionsCount;
    }
}





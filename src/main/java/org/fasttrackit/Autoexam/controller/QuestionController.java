package org.fasttrackit.Autoexam.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fasttrackit.Autoexam.model.Question;
import org.fasttrackit.Autoexam.model.dto.QuestionDTO;
import org.fasttrackit.Autoexam.model.dto.QuestionMapper;
import org.fasttrackit.Autoexam.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService service;

    private final QuestionMapper mapper;
    private Question currentQuestion;
    private int correctAnswersCount;

    @GetMapping
    public List<QuestionDTO> getAllQuestions(Question question) {
        return service.getAllQuestions(question).stream()
                .map(QuestionMapper::toDto)
                .toList();
    }

    @GetMapping("random")
    public Question getRandomQuestion() {
        return service.getRandomQuestion();
    }

    @GetMapping("start-exam")
    public ResponseEntity<String> startExam() {
        if (currentQuestion != null) {
            return ResponseEntity.badRequest().body("Exam has already started.");
        }
        currentQuestion = service.getRandomQuestion();
        correctAnswersCount=0;
        if (currentQuestion != null) {
            return ResponseEntity.ok(currentQuestion.getQuestion());
        } else {
            return ResponseEntity.ok("No questions available for the exam.");
        }
    }

    @PostMapping("submit-answer")
    public ResponseEntity<String> submitAnswer(@RequestParam(required = false) Integer answerIndex) {
        if (currentQuestion == null) {
            return ResponseEntity.badRequest().body("No questions has been asked. Start exam first.");
        }
        if (answerIndex == null || answerIndex < 1 || answerIndex > currentQuestion.getAnswers().size()) {
            return ResponseEntity.badRequest().body("Invalid answer, pick 1-3.");
        }
        currentQuestion.setAnswered(true);
        currentQuestion.setCorrect(answerIndex == currentQuestion.getCorrectAnswerIndex());

        if (currentQuestion.isCorrect()) {
            correctAnswersCount++;
            return ResponseEntity.ok("Correct answer!");
        } else {
            return ResponseEntity.ok("Incorrect answer!");
        }

    }

    @GetMapping("next-question")
    public ResponseEntity<String> nextQuestion() {
        if (currentQuestion == null) {
            return ResponseEntity.badRequest().body("Start exam or; Finish the current question before proceeding to the next question.");
        }

        currentQuestion = service.getRandomQuestion();
        if (currentQuestion != null) {
            return ResponseEntity.ok(currentQuestion.getQuestion());
        } else {
            int totalQuestionsCount = correctAnswersCount + service.getGeneratedQuestionsCount();
            if (correctAnswersCount >= 22) {
                return ResponseEntity.ok("Exam finished. You passed with " + correctAnswersCount + " correct answers out of " + totalQuestionsCount + " questions.");
            } else {
                return ResponseEntity.ok("Exam failed. You answered " + correctAnswersCount + " questions correctly out of " + totalQuestionsCount + " questions.");
            }
//            currentQuestion = null;
//            return ResponseEntity.ok("Exam finished. No more questions available.");
        }
    }

    @GetMapping("/reveal-answer")
    public ResponseEntity<String> revealAnswer() {
        if (currentQuestion == null) {
            return ResponseEntity.badRequest().body("No question has been asked. Start the exam first.");
        }
        if (currentQuestion.isAnswered()) {
            if (!currentQuestion.isCorrect()) {
                String correctAnswer = currentQuestion.getAnswers().get(currentQuestion.getCorrectAnswerIndex() - 1);
                return ResponseEntity.ok("The correct answer is: " + correctAnswer);
            } else {
                return ResponseEntity.ok("You have answered correctly. No need to reveal the answer.");
            }
        } else {
            return ResponseEntity.badRequest().body("You have not answered the question yet.");
        }
    }
}





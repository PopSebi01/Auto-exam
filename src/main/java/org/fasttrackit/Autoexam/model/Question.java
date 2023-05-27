package org.fasttrackit.Autoexam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder(toBuilder = true)
@With
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Question {

   @Id
   @SequenceGenerator(
           name = "question_sequence",
           sequenceName = "question_sequence",
           allocationSize = 1000)

  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "question_sequence")
    private Long id;
    @Column
    private String question;
    @Column
    private List<String> answers;
    @Column
    private int correctAnswerIndex;

    private boolean received = false;
    private boolean isCorrect;
    private boolean isAnswered;

  public Question(String question, List<String> answers, int correctAnswerIndex) {
    this.question = question;
    this.answers = answers;
    this.correctAnswerIndex = correctAnswerIndex;
  }




}

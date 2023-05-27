package org.fasttrackit.Autoexam.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QuestionDTO (
        String question,
        List<String> answers,
      int correctAnswerIndex
)
{

}


package org.fasttrackit.Autoexam.model.dto;

import lombok.Builder;
import org.fasttrackit.Autoexam.model.Question;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class QuestionMapper {
    public static QuestionDTO toDto(Question entity){
        return QuestionDTO.builder()
                .question(String.valueOf(entity.getId()))
                .question(entity.getQuestion())
                .answers(Collections.singletonList(entity.getAnswers().toString()))
                .correctAnswerIndex(entity.getCorrectAnswerIndex())
                .build();
    }
}

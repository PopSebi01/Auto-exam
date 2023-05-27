package org.fasttrackit.Autoexam.service;

import org.fasttrackit.Autoexam.model.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;
import static java.util.stream.Collectors.toList;
@Repository
public class QuestionFileReader implements  FileReader {
    @Value("${file.questions}")
    private String fileQuestionsPath;

    @Override
    public List<Question> populateWithDataFromFile() {
        try {
            return Files.lines(Path.of(fileQuestionsPath))
                    .map(line -> line.split(";"))
                    .map(QuestionFileReader::buildQuestion)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    private static Question buildQuestion(String[] parts) {
        return new Question(parts[0], Arrays.asList(parts[1], parts[2], parts[3]), Integer.parseInt(parts[4]));
    }


}


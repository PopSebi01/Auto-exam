package org.fasttrackit.Autoexam.Exceptions;

public class EndOfQuestionsException extends RuntimeException{
    public EndOfQuestionsException(String errorMessage){
        super(errorMessage);
    }
}

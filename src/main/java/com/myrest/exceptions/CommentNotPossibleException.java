package com.myrest.exceptions;

public class CommentNotPossibleException extends RuntimeException{
    public CommentNotPossibleException(String message){
        super(message);
    }
}

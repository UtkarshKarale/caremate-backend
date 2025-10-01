package com.example.caremate.user.exception;

public class InvalidFormatPasswordException extends RuntimeException {
    public InvalidFormatPasswordException(String message) {
        super(message);
    }
}

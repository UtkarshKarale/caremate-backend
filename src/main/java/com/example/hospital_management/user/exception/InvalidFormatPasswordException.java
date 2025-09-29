package com.example.hospital_management.user.exception;

public class InvalidFormatPasswordException extends RuntimeException {
    public InvalidFormatPasswordException(String message) {
        super(message);
    }
}

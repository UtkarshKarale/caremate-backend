package com.example.caremate.user.exception;

public class MobileAlreadyRegisteredException extends RuntimeException {
    public MobileAlreadyRegisteredException(String message) {
        super(message);
    }
}
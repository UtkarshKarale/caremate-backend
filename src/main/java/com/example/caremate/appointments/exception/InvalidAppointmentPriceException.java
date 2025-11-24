package com.example.caremate.appointments.exception;

public class InvalidAppointmentPriceException extends RuntimeException {
    public InvalidAppointmentPriceException(String message) {
        super(message);
    }
}
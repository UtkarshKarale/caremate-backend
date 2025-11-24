package com.example.caremate.patientRecords.exception;

public class PatientRecordNotFoundException extends RuntimeException {
    public PatientRecordNotFoundException(String message) {
        super(message);
    }
}
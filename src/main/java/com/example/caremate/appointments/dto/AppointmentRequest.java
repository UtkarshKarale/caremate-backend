package com.example.caremate.appointments.dto;

import java.util.Date;

public class AppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private String disease;
    private Date appointmentTime;
    private Double price;

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDisease() {
        return disease;
    }
    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}

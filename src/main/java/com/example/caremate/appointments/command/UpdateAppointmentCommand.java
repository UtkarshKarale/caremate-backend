package com.example.caremate.appointments.command;

import java.util.Date;
import com.example.caremate.appointments.entity.AppointmentStatus;

public class UpdateAppointmentCommand {
    private Long patient;
    private Long doctor;
    private String disease;
    private Date appointmentTime;
    private AppointmentStatus status;
    private Double price;
    private Date completedOn;

    // Getters and setters
    public Long getPatient() { return patient; }
    public void setPatient(Long patient) { this.patient = patient; }

    public Long getDoctor() { return doctor; }
    public void setDoctor(Long doctor) { this.doctor = doctor; }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public Date getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(Date appointmentTime) { this.appointmentTime = appointmentTime; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Date getCompletedOn() { return completedOn; }
    public void setCompletedOn(Date completedOn) { this.completedOn = completedOn; }
}

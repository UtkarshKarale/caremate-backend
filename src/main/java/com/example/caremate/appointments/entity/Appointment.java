package com.example.caremate.appointments.entity;

import com.example.caremate.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    // Doctor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(nullable = false, length = 200)
    private String disease;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "appointment_time", nullable = false)
    private Date appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AppointmentStatus status;

    @Column(nullable = false)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    private Date createdOn = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completed_on")
    private Date completedOn;

    // -------------------- Getters and Setters --------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    // -------------------- Manual Builder --------------------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User patient;
        private User doctor;
        private String disease;
        private Date appointmentTime;
        private AppointmentStatus status;
        private Double price;
        private Date createdOn;
        private Date completedOn;

        public Builder patient(User patient) {
            this.patient = patient;
            return this;
        }

        public Builder doctor(User doctor) {
            this.doctor = doctor;
            return this;
        }

        public Builder disease(String disease) {
            this.disease = disease;
            return this;
        }

        public Builder appointmentTime(Date appointmentTime) {
            this.appointmentTime = appointmentTime;
            return this;
        }

        public Builder status(AppointmentStatus status) {
            this.status = status;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder createdOn(Date createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder completedOn(Date completedOn) {
            this.completedOn = completedOn;
            return this;
        }

        public Appointment build() {
            Appointment appointment = new Appointment();
            appointment.setPatient(this.patient);
            appointment.setDoctor(this.doctor);
            appointment.setDisease(this.disease);
            appointment.setAppointmentTime(this.appointmentTime);
            appointment.setStatus(this.status);
            appointment.setPrice(this.price);
            appointment.setCreatedOn(this.createdOn != null ? this.createdOn : new Date());
            appointment.setCompletedOn(this.completedOn);
            return appointment;
        }
    }
}

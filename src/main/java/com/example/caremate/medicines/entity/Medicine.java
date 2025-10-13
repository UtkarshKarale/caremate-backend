package com.example.caremate.medicines.entity;

import com.example.caremate.prescription.entity.Prescription;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicines")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @Column(name = "medicine_name", nullable = false, length = 200)
    private String medicineName;

    @Column(name = "dosage", nullable = false, length = 100)
    private String dosage; // e.g., "500mg", "10ml"

    @Column(name = "frequency", nullable = false, length = 100)
    private String frequency; // e.g., "Twice daily", "Three times a day"

    @Enumerated(EnumType.STRING)
    @Column(name = "timing", length = 50)
    private MedicineTiming timing; // BEFORE_MEAL, AFTER_MEAL, WITH_MEAL

    @Column(name = "duration", nullable = false, length = 50)
    private String duration; // e.g., "7 days", "2 weeks"

    @Column(name = "instructions", length = 500)
    private String instructions; // Additional instructions

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public MedicineTiming getTiming() {
        return timing;
    }

    public void setTiming(MedicineTiming timing) {
        this.timing = timing;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
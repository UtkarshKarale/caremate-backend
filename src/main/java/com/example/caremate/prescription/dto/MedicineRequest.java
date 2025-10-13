package com.example.caremate.prescription.dto;


import com.example.caremate.medicines.entity.MedicineTiming;

public class MedicineRequest {
    private String medicineName;
    private String dosage;
    private String frequency;
    private MedicineTiming timing;
    private String duration;
    private String instructions;

    public MedicineRequest() {
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
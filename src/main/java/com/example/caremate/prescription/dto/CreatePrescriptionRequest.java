package com.example.caremate.prescription.dto;


import java.util.Date;
import java.util.List;

public class CreatePrescriptionRequest {
    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String symptoms;
    private String notes;
    private Date prescriptionDate;
    private List<MedicineRequest> medicines;

    public CreatePrescriptionRequest() {
    }

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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<MedicineRequest> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineRequest> medicines) {
        this.medicines = medicines;
    }
}
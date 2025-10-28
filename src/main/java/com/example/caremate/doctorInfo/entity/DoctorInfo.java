package com.example.caremate.doctorInfo.entity;

import com.example.caremate.user.entity.User;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "doctor_info")
public class DoctorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapping to User entity (doctor)
    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String qualifications;

    @Column(nullable = false)
    private Integer experienceInYears;

    @Column(nullable = false)
    private Double consultationFee;

    @Column(length = 1000)
    private String bio;

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }
    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualifications() {
        return qualifications;
    }
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }
    public void setExperienceInYears(Integer experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }
    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}

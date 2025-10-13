package com.example.caremate.prescription.repository;

import com.example.caremate.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Find all prescriptions by patient ID
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId ORDER BY p.prescriptionDate DESC")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);

    // Find all prescriptions by doctor ID
    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId ORDER BY p.prescriptionDate DESC")
    List<Prescription> findByDoctorId(@Param("doctorId") Long doctorId);

    // Find prescriptions by patient and date range
    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId " +
           "AND p.prescriptionDate BETWEEN :startDate AND :endDate ORDER BY p.prescriptionDate DESC")
    List<Prescription> findByPatientIdAndDateRange(
            @Param("patientId") Long patientId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    // Find prescriptions by doctor and date range
    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId " +
           "AND p.prescriptionDate BETWEEN :startDate AND :endDate ORDER BY p.prescriptionDate DESC")
    List<Prescription> findByDoctorIdAndDateRange(
            @Param("doctorId") Long doctorId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
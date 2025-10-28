package com.example.caremate.patientRecords.repository;

import com.example.caremate.patientRecords.entity.PatientRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRecordsRepository extends JpaRepository<PatientRecords, Long> {
    
    List<PatientRecords> findByPatientId(Long patientId);
    
    List<PatientRecords> findByAppointmentId(Long appointmentId);
}
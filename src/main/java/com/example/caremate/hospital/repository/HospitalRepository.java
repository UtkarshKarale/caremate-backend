package com.example.caremate.hospital.repository;

import com.example.caremate.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    // Get hospitals by specialization (case-insensitive)
    List<Hospital> findBySpecializationIgnoreCase(String specialization);

    // Get all active hospitals by specialization
    List<Hospital> findBySpecializationIgnoreCaseAndIsActiveTrue(String specialization);
}
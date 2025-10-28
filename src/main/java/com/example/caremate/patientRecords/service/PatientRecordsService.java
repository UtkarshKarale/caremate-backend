package com.example.caremate.patientRecords.service;

import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.repository.AppointmentRepository;
import com.example.caremate.patientRecords.dto.PatientRecordsDTO;
import com.example.caremate.patientRecords.entity.PatientRecords;
import com.example.caremate.patientRecords.repository.PatientRecordsRepository;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientRecordsService {
    
    @Autowired
    private PatientRecordsRepository patientRecordsRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public PatientRecords registerPatientRecord(PatientRecordsDTO dto) {
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + dto.getPatientId()));
        
        PatientRecords record = new PatientRecords();
        record.setPatient(patient);
        
        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + dto.getAppointmentId()));
            record.setAppointment(appointment);
        }
        
        record.setDocumentUrl(dto.getDocumentUrl());
        record.setReportType(dto.getReportType());
        record.setLabName(dto.getLabName());
        record.setDescription(dto.getDescription());
        
        return patientRecordsRepository.save(record);
    }
    
    public List<PatientRecords> getRecordsByUserId(Long userId) {
        return patientRecordsRepository.findByPatientId(userId);
    }
    
    public List<PatientRecords> getRecordsByAppointmentId(Long appointmentId) {
        return patientRecordsRepository.findByAppointmentId(appointmentId);
    }

    public PatientRecords getById(Long id) {
        return patientRecordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient record not found with id: " + id));
    }

    public List<PatientRecords> getAllRecords() {
        return patientRecordsRepository.findAll();
    }
    
    public PatientRecords updatePatientRecord(Long recordId, PatientRecordsDTO dto) {
        PatientRecords existingRecord = patientRecordsRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Patient record not found with id: " + recordId));
        
        if (dto.getDocumentUrl() != null) {
            existingRecord.setDocumentUrl(dto.getDocumentUrl());
        }
        if (dto.getReportType() != null) {
            existingRecord.setReportType(dto.getReportType());
        }
        if (dto.getLabName() != null) {
            existingRecord.setLabName(dto.getLabName());
        }
        if (dto.getDescription() != null) {
            existingRecord.setDescription(dto.getDescription());
        }
        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + dto.getAppointmentId()));
            existingRecord.setAppointment(appointment);
        }
        
        return patientRecordsRepository.save(existingRecord);
    }
}
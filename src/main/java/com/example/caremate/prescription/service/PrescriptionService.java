package com.example.caremate.prescription.service;

import com.example.caremate.medicines.entity.Medicine;
import com.example.caremate.prescription.dto.*;
import com.example.caremate.prescription.entity.Prescription;
import com.example.caremate.prescription.repository.PrescriptionRepository;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PrescriptionResponse createPrescription(CreatePrescriptionRequest request) {
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + request.getDoctorId()));

        // Create prescription
        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setSymptoms(request.getSymptoms());
        prescription.setNotes(request.getNotes());
        prescription.setPrescriptionDate(request.getPrescriptionDate() != null ? request.getPrescriptionDate() : new Date());
        prescription.setCreatedOn(new Date());

        if (request.getMedicines() != null && !request.getMedicines().isEmpty()) {
            for (MedicineRequest medicineReq : request.getMedicines()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineName(medicineReq.getMedicineName());
                medicine.setDosage(medicineReq.getDosage());
                medicine.setFrequency(medicineReq.getFrequency());
                medicine.setTiming(medicineReq.getTiming());
                medicine.setDuration(medicineReq.getDuration());
                medicine.setInstructions(medicineReq.getInstructions());
                prescription.addMedicine(medicine);
            }
        }

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return convertToResponse(savedPrescription);
    }


    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        return convertToResponse(prescription);
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return prescriptions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByPatientId(Long patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId);
        return prescriptions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByDoctorId(Long doctorId) {
        List<Prescription> prescriptions = prescriptionRepository.findByDoctorId(doctorId);
        return prescriptions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByPatientIdAndDateRange(
            Long patientId, Date startDate, Date endDate) {
        List<Prescription> prescriptions = prescriptionRepository
                .findByPatientIdAndDateRange(patientId, startDate, endDate);
        return prescriptions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByDoctorIdAndDateRange(
            Long doctorId, Date startDate, Date endDate) {
        List<Prescription> prescriptions = prescriptionRepository
                .findByDoctorIdAndDateRange(doctorId, startDate, endDate);
        return prescriptions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PrescriptionResponse updatePrescription(Long id, CreatePrescriptionRequest request) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));

        // Update basic fields
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setSymptoms(request.getSymptoms());
        prescription.setNotes(request.getNotes());
        prescription.setPrescriptionDate(request.getPrescriptionDate());
        prescription.setUpdatedOn(new Date());

        // Update medicines - remove old and add new
        prescription.getMedicines().clear();
        if (request.getMedicines() != null && !request.getMedicines().isEmpty()) {
            for (MedicineRequest medicineReq : request.getMedicines()) {
                Medicine medicine = new Medicine();
                medicine.setMedicineName(medicineReq.getMedicineName());
                medicine.setDosage(medicineReq.getDosage());
                medicine.setFrequency(medicineReq.getFrequency());
                medicine.setTiming(medicineReq.getTiming());
                medicine.setDuration(medicineReq.getDuration());
                medicine.setInstructions(medicineReq.getInstructions());
                prescription.addMedicine(medicine);
            }
        }
        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return convertToResponse(updatedPrescription);
    }

    @Transactional
    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    // Convert entity to response DTO
    private PrescriptionResponse convertToResponse(Prescription prescription) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(prescription.getPatient().getId());
        patientInfo.setFullName(prescription.getPatient().getFullName());
        patientInfo.setEmail(prescription.getPatient().getEmail());
        patientInfo.setMobile(prescription.getPatient().getMobile());

        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setId(prescription.getDoctor().getId());
        doctorInfo.setFullName(prescription.getDoctor().getFullName());
        doctorInfo.setEmail(prescription.getDoctor().getEmail());
        doctorInfo.setSpecialist(prescription.getDoctor().getSpecialist());
        doctorInfo.setMobile(prescription.getDoctor().getMobile());

        List<MedicineResponse> medicines = prescription.getMedicines().stream()
                .map(m -> {
                    MedicineResponse medicineResponse = new MedicineResponse();
                    medicineResponse.setId(m.getId());
                    medicineResponse.setMedicineName(m.getMedicineName());
                    medicineResponse.setDosage(m.getDosage());
                    medicineResponse.setFrequency(m.getFrequency());
                    medicineResponse.setTiming(m.getTiming());
                    medicineResponse.setDuration(m.getDuration());
                    medicineResponse.setInstructions(m.getInstructions());
                    return medicineResponse;
                })
                .collect(Collectors.toList());

        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getId());
        response.setPatient(patientInfo);
        response.setDoctor(doctorInfo);
        response.setDiagnosis(prescription.getDiagnosis());
        response.setSymptoms(prescription.getSymptoms());
        response.setNotes(prescription.getNotes());
        response.setPrescriptionDate(prescription.getPrescriptionDate());
        response.setMedicines(medicines);
        response.setCreatedOn(prescription.getCreatedOn());
        response.setUpdatedOn(prescription.getUpdatedOn());

        return response;
    }
}
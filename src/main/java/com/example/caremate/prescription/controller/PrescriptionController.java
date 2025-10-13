package com.example.caremate.prescription.controller;

import com.example.caremate.prescription.dto.CreatePrescriptionRequest;
import com.example.caremate.prescription.dto.PrescriptionResponse;
import com.example.caremate.prescription.service.PrescriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Prescription")
@RestController
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Create new prescription
    @PostMapping("/api/prescriptions/add")
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @RequestBody CreatePrescriptionRequest request) {
        try {
            PrescriptionResponse response = prescriptionService.createPrescription(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get prescription by ID
    @GetMapping("/api/prescriptions/{id}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable Long id) {
        try {
            PrescriptionResponse response = prescriptionService.getPrescriptionById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all prescriptions
    @GetMapping("/api/prescriptions/all")
    public ResponseEntity<List<PrescriptionResponse>> getAllPrescriptions() {
        try {
            List<PrescriptionResponse> prescriptions = prescriptionService.getAllPrescriptions();
            return new ResponseEntity<>(prescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get prescriptions by patient ID
    @GetMapping("/api/prescriptions/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPatientId(
            @PathVariable Long patientId) {
        try {
            List<PrescriptionResponse> prescriptions = 
                    prescriptionService.getPrescriptionsByPatientId(patientId);
            return new ResponseEntity<>(prescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get prescriptions by doctor ID
    @GetMapping("/api/prescriptions/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByDoctorId(
            @PathVariable Long doctorId) {
        try {
            List<PrescriptionResponse> prescriptions = 
                    prescriptionService.getPrescriptionsByDoctorId(doctorId);
            return new ResponseEntity<>(prescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get prescriptions by patient ID and date range
    @GetMapping("/api/prescriptions/patient/{patientId}/date-range")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPatientIdAndDateRange(
            @PathVariable Long patientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<PrescriptionResponse> prescriptions = 
                    prescriptionService.getPrescriptionsByPatientIdAndDateRange(
                            patientId, startDate, endDate);
            return new ResponseEntity<>(prescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get prescriptions by doctor ID and date range
    @GetMapping("/api/prescriptions/doctor/{doctorId}/date-range")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByDoctorIdAndDateRange(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<PrescriptionResponse> prescriptions = 
                    prescriptionService.getPrescriptionsByDoctorIdAndDateRange(
                            doctorId, startDate, endDate);
            return new ResponseEntity<>(prescriptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update prescription
    @PutMapping("/api/prescriptions/update/{id}")
    public ResponseEntity<PrescriptionResponse> updatePrescription(
            @PathVariable Long id,
            @RequestBody CreatePrescriptionRequest request) {
        try {
            PrescriptionResponse response = prescriptionService.updatePrescription(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete prescription
    @DeleteMapping("/api/prescriptions/delete/{id}")
    public ResponseEntity<HttpStatus> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescription(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
package com.example.caremate.hospital.controller;

import com.example.caremate.hospital.dto.AssignDoctorsRequest;
import com.example.caremate.hospital.dto.HospitalDTO;
import com.example.caremate.hospital.dto.HospitalDoctorDTO;
import com.example.caremate.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hospital")
@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody HospitalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hospitalService.createHospital(dto));
    }

    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Integer id) {
        return ResponseEntity.ok(hospitalService.getHospitalById(id));
    }

    @GetMapping("/specialization")
    public ResponseEntity<List<HospitalDTO>> getBySpecialization(@RequestParam String name) {
        return ResponseEntity.ok(hospitalService.getHospitalsBySpecialization(name));
    }

    @GetMapping("/doctors/available")
    public ResponseEntity<List<HospitalDoctorDTO>> getAvailableDoctors() {
        return ResponseEntity.ok(hospitalService.getAvailableDoctors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@PathVariable Integer id, @RequestBody HospitalDTO dto) {
        return ResponseEntity.ok(hospitalService.updateHospital(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok("Hospital deleted successfully");
    }

    @PutMapping("/{hospitalId}/doctors")
    public ResponseEntity<HospitalDTO> replaceAssignedDoctors(
            @PathVariable Integer hospitalId,
            @RequestBody AssignDoctorsRequest request
    ) {
        return ResponseEntity.ok(hospitalService.replaceAssignedDoctors(hospitalId, request.getDoctorIds()));
    }

    @PostMapping("/{hospitalId}/doctors/{doctorId}")
    public ResponseEntity<HospitalDTO> assignDoctor(
            @PathVariable Integer hospitalId,
            @PathVariable Long doctorId
    ) {
        return ResponseEntity.ok(hospitalService.assignDoctor(hospitalId, doctorId));
    }

    @DeleteMapping("/{hospitalId}/doctors/{doctorId}")
    public ResponseEntity<HospitalDTO> unassignDoctor(
            @PathVariable Integer hospitalId,
            @PathVariable Long doctorId
    ) {
        return ResponseEntity.ok(hospitalService.unassignDoctor(hospitalId, doctorId));
    }
}

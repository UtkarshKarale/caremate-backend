package com.example.caremate.hospital.controller;

import com.example.caremate.hospital.dto.HospitalDTO;
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

    // POST /api/hospitals
    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@RequestBody HospitalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hospitalService.createHospital(dto));
    }

    // GET /api/hospitals
    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    // GET /api/hospitals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Integer id) {
        return ResponseEntity.ok(hospitalService.getHospitalById(id));
    }

    // GET /api/hospitals/specialization?name=Cardiology
    @GetMapping("/specialization")
    public ResponseEntity<List<HospitalDTO>> getBySpecialization(
            @RequestParam String name) {
        return ResponseEntity.ok(hospitalService.getHospitalsBySpecialization(name));
    }

    // PUT /api/hospitals/{id}
    @PutMapping("/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(
            @PathVariable Integer id, @RequestBody HospitalDTO dto) {
        return ResponseEntity.ok(hospitalService.updateHospital(id, dto));
    }

    // DELETE /api/hospitals/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.ok("Hospital deleted successfully");
    }
}
package com.example.caremate.patientRecords.controller;

import com.example.caremate.patientRecords.dto.PatientRecordsDTO;
import com.example.caremate.patientRecords.entity.PatientRecords;
import com.example.caremate.patientRecords.service.PatientRecordsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/records")
@Tag(name = "PatientRecords")
public class PatientRecordsController {
    
    @Autowired
    private PatientRecordsService patientRecordsService;
    
    @PostMapping("/add")
    public ResponseEntity<PatientRecords> registerPatientRecord(@RequestBody PatientRecordsDTO dto) {
        PatientRecords record = patientRecordsService.registerPatientRecord(dto);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PatientRecords>> getRecordsByUserId(@PathVariable Long userId) {
        List<PatientRecords> records = patientRecordsService.getRecordsByUserId(userId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<PatientRecords>> getRecordsByAppointmentId(@PathVariable Long appointmentId) {
        List<PatientRecords> records = patientRecordsService.getRecordsByAppointmentId(appointmentId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientRecords> getRecordById(@PathVariable Long id){
        PatientRecords records = patientRecordsService.getById(id);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientRecords>> getAllRecords() {
        List<PatientRecords> records = patientRecordsService.getAllRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @PutMapping("/update/{recordId}")
    public ResponseEntity<PatientRecords> updatePatientRecord(@PathVariable Long recordId,
                                                               @RequestBody PatientRecordsDTO dto) {
        PatientRecords updatedRecord = patientRecordsService.updatePatientRecord(recordId, dto);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }
}
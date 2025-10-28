package com.example.caremate.appointments.controller;

import com.example.caremate.appointments.command.UpdateAppointmentCommand;
import com.example.caremate.appointments.dto.AppointmentRequest;
import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.entity.AppointmentStatus;
import com.example.caremate.appointments.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/apply")
    public Appointment applyAppointment(@RequestBody AppointmentRequest request) {
        return appointmentService.applyAppointment(request);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/today")
    public List<Appointment> getTodaysAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getTodaysAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/date/{date}")
    public List<Appointment> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return appointmentService.getAppointmentsByDate(date);
    }

    @GetMapping("/status")
    public List<Appointment> getAppointmentsByStatus(@RequestParam AppointmentStatus status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/all")
    public List<Appointment> getAppointmentsByStatus() {
        return appointmentService.geAllAppointments();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody UpdateAppointmentCommand command) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, command);
        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping("/latest")
    public List<Appointment> getLatestAppointments(
            @RequestParam(defaultValue = "5") int limit) {
        return appointmentService.getLatestAppointments(limit);
    }
}

package com.example.caremate.appointments.service;

import com.example.caremate.appointments.command.UpdateAppointmentCommand;
import com.example.caremate.appointments.dto.AppointmentRequest;
import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.entity.AppointmentStatus;
import com.example.caremate.appointments.repository.AppointmentRepository;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public Appointment applyAppointment(AppointmentRequest request) {
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User doctor = null;
        if (request.getDoctorId() != null) {
            doctor = userRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
        }

        if (request.getPrice() == null || request.getPrice() < 0) {
            throw new RuntimeException("Invalid appointment price");
        }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .disease(request.getDisease())
                .appointmentTime(request.getAppointmentTime())
                .status(AppointmentStatus.PENDING)
                .price(request.getPrice())
                .createdOn(new Date())
                .build();

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDate(Date date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment updateAppointment(Long id, UpdateAppointmentCommand command) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        // Partial updates
        if (command.getPatient() != null) {
            User patient = userRepository.findById(command.getPatient())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
            appointment.setPatient(patient);
        }

        if (command.getDoctor() != null) {
            User doctor = userRepository.findById(command.getDoctor())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
            appointment.setDoctor(doctor);
        }
        if (command.getDisease() != null) appointment.setDisease(command.getDisease());
        if (command.getAppointmentTime() != null) appointment.setAppointmentTime(command.getAppointmentTime());
        if (command.getStatus() != null) appointment.setStatus(command.getStatus());
        if (command.getPrice() != null) appointment.setPrice(command.getPrice());
        if (command.getCompletedOn() != null) appointment.setCompletedOn(command.getCompletedOn());

        return appointmentRepository.save(appointment);
    }

}

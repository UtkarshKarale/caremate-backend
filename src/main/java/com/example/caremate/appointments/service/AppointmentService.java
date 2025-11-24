package com.example.caremate.appointments.service;

import com.example.caremate.appointments.command.UpdateAppointmentCommand;
import com.example.caremate.appointments.dto.AppointmentRequest;
import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.entity.AppointmentStatus;
import com.example.caremate.appointments.exception.InvalidAppointmentPriceException;
import com.example.caremate.appointments.repository.AppointmentRepository;
import com.example.caremate.patientRecords.exception.AppointmentNotFoundException;
import com.example.caremate.prescription.exception.DoctorNotFoundException;
import com.example.caremate.prescription.exception.PatientNotFoundException;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + request.getPatientId()));

        User doctor = null;
        if (request.getDoctorId() != null) {
            doctor = userRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + request.getDoctorId()));
        }

        if (request.getPrice() == null || request.getPrice() < 0) {
            throw new InvalidAppointmentPriceException("Invalid appointment price: " + request.getPrice());
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

    public List<Appointment> getTodaysAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findTodaysByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDate(Date date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment updateAppointment(Long id, UpdateAppointmentCommand command) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

        if (command.getPatient() != null) {
            User patient = userRepository.findById(command.getPatient())
                    .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + command.getPatient()));
            appointment.setPatient(patient);
        }

        if (command.getDoctor() != null) {
            User doctor = userRepository.findById(command.getDoctor())
                    .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + command.getDoctor()));
            appointment.setDoctor(doctor);
        }

        if (command.getDisease() != null) appointment.setDisease(command.getDisease());
        if (command.getAppointmentTime() != null) appointment.setAppointmentTime(command.getAppointmentTime());
        if (command.getStatus() != null) appointment.setStatus(command.getStatus());
        if (command.getPrice() != null) appointment.setPrice(command.getPrice());
        if (command.getCompletedOn() != null) appointment.setCompletedOn(command.getCompletedOn());

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> geAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getLatestAppointments(int limit) {
        return appointmentRepository.findLatestAppointments((Pageable) PageRequest.of(0, limit));
    }
}
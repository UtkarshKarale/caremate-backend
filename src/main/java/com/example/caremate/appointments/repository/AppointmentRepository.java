package com.example.caremate.appointments.repository;

import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentTime) = DATE(?1)")
    List<Appointment> findByAppointmentDate(Date appointmentDate);

    @Query("SELECT a FROM Appointment a WHERE a.status = :status ORDER BY a.appointmentTime DESC")
    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findTodaysByDoctorId(Long doctorId);
}

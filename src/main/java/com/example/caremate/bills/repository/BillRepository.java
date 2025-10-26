package com.example.caremate.bills.repository;

import com.example.caremate.bills.entity.Bill;
import com.example.caremate.bills.entity.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Find by bill number
    Optional<Bill> findByBillNumber(String billNumber);

    // Find all bills by patient ID
    @Query("SELECT b FROM Bill b WHERE b.patient.id = :patientId ORDER BY b.billDate DESC")
    List<Bill> findByPatientId(@Param("patientId") Long patientId);

    // Find bills by appointment ID
    @Query("SELECT b FROM Bill b WHERE b.appointment.id = :appointmentId")
    Optional<Bill> findByAppointmentId(@Param("appointmentId") Long appointmentId);

    // Find bills by status
    @Query("SELECT b FROM Bill b WHERE b.status = :status ORDER BY b.billDate DESC")
    List<Bill> findByStatus(@Param("status") BillStatus status);

    // Find bills by patient and status
    @Query("SELECT b FROM Bill b WHERE b.patient.id = :patientId AND b.status = :status ORDER BY b.billDate DESC")
    List<Bill> findByPatientIdAndStatus(@Param("patientId") Long patientId, @Param("status") BillStatus status);

    // Find bills by date range
    @Query("SELECT b FROM Bill b WHERE b.billDate BETWEEN :startDate AND :endDate ORDER BY b.billDate DESC")
    List<Bill> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Find pending bills
    @Query("SELECT b FROM Bill b WHERE b.status = 'PENDING' OR b.status = 'PARTIALLY_PAID' ORDER BY b.billDate DESC")
    List<Bill> findPendingBills();

    // Find pending bills by patient
    @Query("SELECT b FROM Bill b WHERE b.patient.id = :patientId AND (b.status = 'PENDING' OR b.status = 'PARTIALLY_PAID') ORDER BY b.billDate DESC")
    List<Bill> findPendingBillsByPatientId(@Param("patientId") Long patientId);
}
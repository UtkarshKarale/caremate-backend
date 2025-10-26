package com.example.caremate.bills.service;

import com.example.caremate.appointments.entity.Appointment;
import com.example.caremate.appointments.repository.AppointmentRepository;
import com.example.caremate.bills.dto.*;
import com.example.caremate.bills.entity.Bill;
import com.example.caremate.bills.entity.BillItem;
import com.example.caremate.bills.entity.BillStatus;
import com.example.caremate.bills.repository.BillRepository;
import com.example.caremate.prescription.dto.PatientInfo;
import com.example.caremate.prescription.entity.Prescription;
import com.example.caremate.prescription.repository.PrescriptionRepository;
import com.example.caremate.user.entity.User;
import com.example.caremate.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Transactional
    public BillResponse createBill(CreateBillRequest request) {
        // Validate patient
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + request.getPatientId()));

        // Create bill
        Bill bill = new Bill();
        bill.setBillNumber(generateBillNumber());
        bill.setPatient(patient);
        bill.setBillDate(request.getBillDate() != null ? request.getBillDate() : new Date());
        bill.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO);
        bill.setTaxAmount(request.getTaxAmount() != null ? request.getTaxAmount() : BigDecimal.ZERO);
        bill.setPaidAmount(BigDecimal.ZERO);
        bill.setStatus(BillStatus.PENDING);
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setNotes(request.getNotes());
        bill.setCreatedOn(new Date());

        // Set appointment if provided
        if (request.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + request.getAppointmentId()));
            bill.setAppointment(appointment);
        }

        // Add bill items
        if (request.getBillItems() != null && !request.getBillItems().isEmpty()) {
            for (BillItemRequest itemReq : request.getBillItems()) {
                BillItem billItem = new BillItem();
                billItem.setItemType(itemReq.getItemType());
                billItem.setItemName(itemReq.getItemName());
                billItem.setDescription(itemReq.getDescription());
                billItem.setQuantity(itemReq.getQuantity());
                billItem.setUnitPrice(itemReq.getUnitPrice());
                billItem.setTotalPrice(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));

                // Set prescription if provided
                if (itemReq.getPrescriptionId() != null) {
                    Prescription prescription = prescriptionRepository.findById(itemReq.getPrescriptionId())
                            .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + itemReq.getPrescriptionId()));
                    billItem.setPrescription(prescription);
                }

                bill.addBillItem(billItem);
            }
        }

        // Calculate totals
        bill.calculateTotals();

        Bill savedBill = billRepository.save(bill);
        return convertToResponse(savedBill);
    }

    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
        return convertToResponse(bill);
    }

    public BillResponse getBillByNumber(String billNumber) {
        Bill bill = billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new RuntimeException("Bill not found with number: " + billNumber));
        return convertToResponse(bill);
    }

    public List<BillResponse> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByPatientId(Long patientId) {
        List<Bill> bills = billRepository.findByPatientId(patientId);
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public BillResponse getBillByAppointmentId(Long appointmentId) {
        Bill bill = billRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Bill not found for appointment id: " + appointmentId));
        return convertToResponse(bill);
    }

    public List<BillResponse> getBillsByStatus(BillStatus status) {
        List<Bill> bills = billRepository.findByStatus(status);
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByPatientIdAndStatus(Long patientId, BillStatus status) {
        List<Bill> bills = billRepository.findByPatientIdAndStatus(patientId, status);
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getBillsByDateRange(Date startDate, Date endDate) {
        List<Bill> bills = billRepository.findByDateRange(startDate, endDate);
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getPendingBills() {
        List<Bill> bills = billRepository.findPendingBills();
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<BillResponse> getPendingBillsByPatientId(Long patientId) {
        List<Bill> bills = billRepository.findPendingBillsByPatientId(patientId);
        return bills.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BillResponse updateBill(Long id, CreateBillRequest request) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        // Update basic fields
        bill.setBillDate(request.getBillDate());
        bill.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : BigDecimal.ZERO);
        bill.setTaxAmount(request.getTaxAmount() != null ? request.getTaxAmount() : BigDecimal.ZERO);
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setNotes(request.getNotes());
        bill.setUpdatedOn(new Date());

        // Update bill items - remove old and add new
        bill.getBillItems().clear();
        if (request.getBillItems() != null && !request.getBillItems().isEmpty()) {
            for (BillItemRequest itemReq : request.getBillItems()) {
                BillItem billItem = new BillItem();
                billItem.setItemType(itemReq.getItemType());
                billItem.setItemName(itemReq.getItemName());
                billItem.setDescription(itemReq.getDescription());
                billItem.setQuantity(itemReq.getQuantity());
                billItem.setUnitPrice(itemReq.getUnitPrice());
                billItem.setTotalPrice(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));

                if (itemReq.getPrescriptionId() != null) {
                    Prescription prescription = prescriptionRepository.findById(itemReq.getPrescriptionId())
                            .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + itemReq.getPrescriptionId()));
                    billItem.setPrescription(prescription);
                }

                bill.addBillItem(billItem);
            }
        }

        // Recalculate totals
        bill.calculateTotals();

        Bill updatedBill = billRepository.save(bill);
        return convertToResponse(updatedBill);
    }

    @Transactional
    public BillResponse makePayment(Long id, PaymentRequest request) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        BigDecimal currentPaid = bill.getPaidAmount() != null ? bill.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal newPaidAmount = currentPaid.add(request.getAmount());

        bill.setPaidAmount(newPaidAmount);
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setDueAmount(bill.getTotalAmount().subtract(newPaidAmount));

        // Update status based on payment
        if (bill.getDueAmount().compareTo(BigDecimal.ZERO) == 0) {
            bill.setStatus(BillStatus.PAID);
        } else if (newPaidAmount.compareTo(BigDecimal.ZERO) > 0) {
            bill.setStatus(BillStatus.PARTIALLY_PAID);
        }

        bill.setUpdatedOn(new Date());

        Bill updatedBill = billRepository.save(bill);
        return convertToResponse(updatedBill);
    }

    @Transactional
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new RuntimeException("Bill not found with id: " + id);
        }
        billRepository.deleteById(id);
    }

    @Transactional
    public BillResponse cancelBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));

        bill.setStatus(BillStatus.CANCELLED);
        bill.setUpdatedOn(new Date());

        Bill cancelledBill = billRepository.save(bill);
        return convertToResponse(cancelledBill);
    }

    // Helper method to generate unique bill number
    private String generateBillNumber() {
        return "BILL-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Convert entity to response DTO
    private BillResponse convertToResponse(Bill bill) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(bill.getPatient().getId());
        patientInfo.setFullName(bill.getPatient().getFullName());
        patientInfo.setEmail(bill.getPatient().getEmail());
        patientInfo.setMobile(bill.getPatient().getMobile());

        AppointmentInfo appointmentInfo = null;
        if (bill.getAppointment() != null) {
            appointmentInfo = new AppointmentInfo();
            appointmentInfo.setId(bill.getAppointment().getId());
            appointmentInfo.setAppointmentDate(bill.getAppointment().getAppointmentTime());
            if (bill.getAppointment().getDoctor() != null) {
                appointmentInfo.setDoctorName(bill.getAppointment().getDoctor().getFullName());
            }
        }

        List<BillItemResponse> billItems = bill.getBillItems().stream()
                .map(item -> {
                    BillItemResponse itemResponse = new BillItemResponse();
                    itemResponse.setId(item.getId());
                    itemResponse.setItemType(item.getItemType());
                    itemResponse.setItemName(item.getItemName());
                    itemResponse.setDescription(item.getDescription());
                    itemResponse.setQuantity(item.getQuantity());
                    itemResponse.setUnitPrice(item.getUnitPrice());
                    itemResponse.setTotalPrice(item.getTotalPrice());
                    return itemResponse;
                })
                .collect(Collectors.toList());

        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setBillNumber(bill.getBillNumber());
        response.setPatient(patientInfo);
        response.setAppointment(appointmentInfo);
        response.setBillDate(bill.getBillDate());
        response.setTotalAmount(bill.getTotalAmount());
        response.setDiscountAmount(bill.getDiscountAmount());
        response.setTaxAmount(bill.getTaxAmount());
        response.setPaidAmount(bill.getPaidAmount());
        response.setDueAmount(bill.getDueAmount());
        response.setStatus(bill.getStatus());
        response.setPaymentMethod(bill.getPaymentMethod());
        response.setNotes(bill.getNotes());
        response.setBillItems(billItems);
        response.setCreatedOn(bill.getCreatedOn());
        response.setUpdatedOn(bill.getUpdatedOn());

        return response;
    }
}
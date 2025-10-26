package com.example.caremate.bills.dto;

import com.example.caremate.bills.entity.BillStatus;
import com.example.caremate.bills.entity.PaymentMethod;
import com.example.caremate.prescription.dto.PatientInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BillResponse {
    private Long id;
    private String billNumber;
    private PatientInfo patient;
    private AppointmentInfo appointment;
    private Date billDate;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal paidAmount;
    private BigDecimal dueAmount;
    private BillStatus status;
    private PaymentMethod paymentMethod;
    private String notes;
    private List<BillItemResponse> billItems;
    private Date createdOn;
    private Date updatedOn;

    public BillResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public PatientInfo getPatient() {
        return patient;
    }

    public void setPatient(PatientInfo patient) {
        this.patient = patient;
    }

    public AppointmentInfo getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentInfo appointment) {
        this.appointment = appointment;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<BillItemResponse> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemResponse> billItems) {
        this.billItems = billItems;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
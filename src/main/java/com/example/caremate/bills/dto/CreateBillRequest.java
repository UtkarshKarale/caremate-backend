package com.example.caremate.bills.dto;



import com.example.caremate.bills.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

// Create Bill Request DTO
public class CreateBillRequest {
    private Long patientId;
    private Long appointmentId;
    private Date billDate;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private PaymentMethod paymentMethod;
    private String notes;
    private List<BillItemRequest> billItems;

    public CreateBillRequest() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
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

    public List<BillItemRequest> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemRequest> billItems) {
        this.billItems = billItems;
    }
}

package com.example.caremate.bills.dto;

import com.example.caremate.bills.entity.PaymentMethod;

import java.math.BigDecimal;

public class PaymentRequest {
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String notes;

    public PaymentRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
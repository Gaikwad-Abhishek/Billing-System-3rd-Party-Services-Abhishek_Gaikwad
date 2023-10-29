package com.invoiceservice.entity;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.invoiceservice.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invoice {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private Long familyAccountId;
    private BigDecimal totalAmount;
    private LocalDate invoiceDate;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    // Other fields, getters, and setters

    @OneToMany(mappedBy = "invoice")
    private List<UserSubscriptionRecord> userSubscriptions;
    
    public Invoice() {
        this.paymentStatus = PaymentStatus.UNPAID; // Set the default value to 0
    }
    
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getFamilyAccountId() {
        return familyAccountId;
    }

    public void setFamilyAccountId(Long familyAccountId) {
        this.familyAccountId = familyAccountId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<UserSubscriptionRecord> getUserSubscriptions() {
        return userSubscriptions;
    }

    public void setUserSubscriptions(List<UserSubscriptionRecord> userSubscriptions) {
        this.userSubscriptions = userSubscriptions;
    }
    
}


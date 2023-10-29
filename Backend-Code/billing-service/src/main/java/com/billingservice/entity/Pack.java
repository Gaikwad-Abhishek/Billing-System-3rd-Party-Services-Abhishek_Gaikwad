package com.billingservice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;
    private BigDecimal price;
    private String description; // Add other necessary details as needed

    // Constructors, getters, and setters

    public Pack() {
        // Default constructor
    }

    public Pack(String providerName, BigDecimal price, String description) {
        this.providerName = providerName;
        this.price = price;
        this.description = description;
    }

    // Getters and setters
}

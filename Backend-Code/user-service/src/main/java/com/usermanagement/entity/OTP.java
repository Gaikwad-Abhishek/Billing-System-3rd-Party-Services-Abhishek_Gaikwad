package com.usermanagement.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String contactNo;
    private String otpValue;
    private Date expiryDate;

    @OneToOne
    private User user;
    
    public OTP() {
    }
    public OTP(String otpValue, Date expiryDate, User user) {
        this.otpValue = otpValue;
        this.expiryDate = expiryDate;
        this.user = user;
    }
    
}
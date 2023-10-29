package com.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(unique = true) 
    private String username;
    
    @Column(unique = true) 
    private String email;
    
    private String password;
    private String firstName;
    private String lastName;
    
    @Column(unique = true)
    private String contactNo; 
    
    @ManyToOne
    @JoinColumn(name = "family_account_id") 
    private FamilyAccount familyAccount;
       
    public Long getUserId() {
    	return userId;
    }
}


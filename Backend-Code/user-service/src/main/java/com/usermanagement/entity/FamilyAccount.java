package com.usermanagement.entity;

import java.util.List;

import com.usermanagement.enums.FamilyAccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FamilyAccount {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyAccountId; 
    
    @ManyToOne
    @JoinColumn(name = "primary_user_id")
    private User primaryUser;
    
    @OneToMany(mappedBy = "familyAccount")
    private List<User> secondaryUsers;
    

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private FamilyAccountStatus accountStatus;

    // Constructor with default value
    public FamilyAccount() {
        this.accountStatus = FamilyAccountStatus.INACTIVE; // Set the default value to 0
    }
    
}


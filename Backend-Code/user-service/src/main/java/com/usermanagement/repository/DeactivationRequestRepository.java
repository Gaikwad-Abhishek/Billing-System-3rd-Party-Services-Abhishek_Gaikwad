package com.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagement.entity.DeactivationRequest;

public interface DeactivationRequestRepository extends JpaRepository<DeactivationRequest, Long> {
    
}
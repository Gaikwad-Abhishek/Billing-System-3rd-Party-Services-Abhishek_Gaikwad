package com.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billingservice.entity.Pack;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    
}


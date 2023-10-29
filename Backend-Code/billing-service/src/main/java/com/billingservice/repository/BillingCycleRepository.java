package com.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.billingservice.entity.BillingCycle;

@Repository
public interface BillingCycleRepository extends JpaRepository<BillingCycle, Long> {
    
    @Query("SELECT bc FROM BillingCycle bc ORDER BY bc.lastExecutionTimestamp DESC")
    BillingCycle findLastBillingCycle();
    
}
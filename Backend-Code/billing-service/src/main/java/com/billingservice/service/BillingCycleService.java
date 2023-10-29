package com.billingservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billingservice.entity.BillingCycle;
import com.billingservice.repository.BillingCycleRepository;

@Service
public class BillingCycleService {

    @Autowired
    private BillingCycleRepository billingCycleRepository;

    public BillingCycle getLastBillingCycle() {
        return billingCycleRepository.findLastBillingCycle();
    }
    
    public void createBillingCycle(LocalDateTime lastBillingExecution) {
    	BillingCycle billingCycle = new BillingCycle();
    	billingCycle.setLastExecutionTimestamp(lastBillingExecution);
    	billingCycleRepository.save(billingCycle);
    }
}
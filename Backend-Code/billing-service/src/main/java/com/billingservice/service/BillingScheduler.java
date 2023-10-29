package com.billingservice.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billingservice.DTO.FamilySubscriptionDTO;
import com.billingservice.client.InvoiceServiceCommunication;

@Service
public class BillingScheduler {

    @Autowired
    private InvoiceServiceCommunication invoiceServiceCommunication;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private BillingCycleService billingCycleService;

    private LocalDateTime lastExecutionTime;    
    private final int billingCycleDuration = 30;

//    @Scheduled(cron = "0 0 0 * * ?") // Execute daily at midnight
//    @Scheduled(fixedRate = 10000)
    public void runBillingService() {
    	
    	lastExecutionTime = billingCycleService.getLastBillingCycle().getLastExecutionTimestamp();
    	
        // Calculate the time since the last execution
        Duration timeSinceLastExecution = Duration.between(lastExecutionTime, LocalDateTime.now());
        
        // If 30 days completed, execute the billing service
        if (timeSinceLastExecution.toDays() == billingCycleDuration) {
            // Execute the billing service
        	
        	List<FamilySubscriptionDTO> familySubscriptions = subscriptionService.getFamilySubscriptions();
        	invoiceServiceCommunication.generateInvoice(familySubscriptions);
        	invoiceServiceCommunication.generateInvoiceEmail(familySubscriptions);
            
        	// Update the last execution time to the next day and store it in the database
            lastExecutionTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
            billingCycleService.createBillingCycle(lastExecutionTime);
        }
    }
    
}


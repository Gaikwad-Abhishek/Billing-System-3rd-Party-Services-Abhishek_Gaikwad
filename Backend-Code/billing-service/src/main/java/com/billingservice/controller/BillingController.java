package com.billingservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billingservice.DTO.FamilySubscriptionDTO;
import com.billingservice.client.InvoiceServiceCommunication;
import com.billingservice.client.UserServiceCommunication;
import com.billingservice.entity.Pack;
import com.billingservice.entity.Subscription;
import com.billingservice.service.BillingCycleService;
import com.billingservice.service.JwtService;
import com.billingservice.service.PackService;
//import com.billingservice.service.MessageProducerService;
import com.billingservice.service.SubscriptionService;

@RestController
@RequestMapping("api/family-subscriptions")
public class BillingController {
	
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private PackService packService; 
    
    @Autowired
    private InvoiceServiceCommunication invoiceServiceCommunication;
    
    @Autowired
    private BillingCycleService billingCycleService;
    
    @Autowired
    UserServiceCommunication userServiceCommunication;
    
    @GetMapping("/list")
    public ResponseEntity<List<FamilySubscriptionDTO>> getFamilySubscriptions() {
    	
        List<FamilySubscriptionDTO> familySubscriptions = subscriptionService.getFamilySubscriptions();
        return new ResponseEntity<>(familySubscriptions, HttpStatus.OK);
    
    }
    
    @GetMapping("/trigger-billing")
    public ResponseEntity<String> triggerBilling() {
        
    	List<FamilySubscriptionDTO> familySubscriptions = subscriptionService.getFamilySubscriptions();
    	invoiceServiceCommunication.generateInvoice(familySubscriptions);
    	invoiceServiceCommunication.generateInvoiceEmail(familySubscriptions);
    	userServiceCommunication.deactivateAccounts();
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }
    
    @PostMapping("/update-billing-date")
    public ResponseEntity<String> addSubscription() {
    	billingCycleService.createBillingCycle(LocalDateTime.now());
    	return ResponseEntity.ok("Billing Date Update");
    }
    
    @PostMapping("{familyAccountId}/add/{packId}")
    public ResponseEntity<String> addSubscription(
            @PathVariable Long familyAccountId,
            @PathVariable Long packId,
            @RequestHeader("Authorization")String jwtToken
    ) {
    	Long userId = jwtService.getUserId(jwtToken);
    	subscriptionService.createSubscription(familyAccountId, userId, packId);
    	return ResponseEntity.ok("Subscription Created");
    }
    
    @PostMapping("/remove/{subscriptionId}")
    public ResponseEntity<String> removeSubscription(
            @PathVariable Long subscriptionId
    ) {
    	subscriptionService.removeSubscription(subscriptionId);
    	return ResponseEntity.ok("Subscription Removed");
    }
    
    @PostMapping("/remove-user-subscriptions/{userId}")
    public ResponseEntity<String> removeUserSubscriptions(
            @PathVariable Long userId
    ) {
    	subscriptionService.removeUserSubscriptions(userId);
    	return ResponseEntity.ok("Subscription Removed");
    }
    
    @GetMapping("/available-packs")
    public List<Pack> getPackInformation() {
    	return packService.getAllPacks();
    }
    
    @PostMapping("/{familyAccountId}")
    public List<Subscription> getSubscriptionsByFamilyAccountId(@PathVariable Long familyAccountId) {
        return subscriptionService.getSubscriptionsByFamilyAccountId(familyAccountId);
    }

}


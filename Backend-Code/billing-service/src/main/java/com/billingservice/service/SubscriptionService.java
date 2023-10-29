package com.billingservice.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.billingservice.DTO.FamilySubscriptionDTO;
import com.billingservice.DTO.UserSubscriptionDTO;
import com.billingservice.client.InvoiceServiceCommunication;
import com.billingservice.client.UserServiceCommunication;
import com.billingservice.entity.Pack;
import com.billingservice.entity.Subscription;
import com.billingservice.exception.DuplicateSubscriptionException;
import com.billingservice.exception.SubscriptionNotFoundException;
import com.billingservice.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
	
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    UserServiceCommunication userServiceCommunication;
    
    @Autowired
    InvoiceServiceCommunication invoiceServiceCommunication;
    
    @Autowired
    PackService packService;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException("Subscription with ID " + subscriptionId + " not found"));
    }
    
    public List<Subscription> getSubscriptionsByFamilyAccountId(Long familyAccountId) {
        return subscriptionRepository.findByFamilyAccountId(familyAccountId);
    }

    public Subscription createSubscription(Long familyAccountId, Long userId, Long packId) {
       
    	Subscription subscription = new Subscription();
        subscription.setFamilyAccountId(familyAccountId);
        subscription.setUserId(userId);
        
        // Fetch the Pack object based on the provided packId
        Pack pack = packService.getPackById(packId);
        subscription.setPack(pack);
        
        try {
            // Save the subscription
            return subscriptionRepository.save(subscription);
        } catch (DataIntegrityViolationException ex) {
                throw new DuplicateSubscriptionException("A duplicate subscription already exists for the provided user and pack.", ex);
         
        }
    }

    public Subscription updateSubscription(Long subscriptionId, Subscription updatedSubscription) {
        // Check if the subscription with the given ID exists
        Subscription existingSubscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new SubscriptionNotFoundException("Subscription with ID " + subscriptionId + " not found"));

        // Update the existing subscription's properties
        existingSubscription.setUserId(updatedSubscription.getUserId());
        existingSubscription.setFamilyAccountId(updatedSubscription.getFamilyAccountId());
        existingSubscription.setPack(updatedSubscription.getPack());

        return subscriptionRepository.save(existingSubscription);
    }

    public void removeSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }
    
    public void removeUserSubscriptions(Long userId) {
        subscriptionRepository.deleteAllByUserId(userId);
    }
          
    public List<FamilySubscriptionDTO> getFamilySubscriptions() {
    	
    	List<Long> activeAccountIds = userServiceCommunication.getActiveAccounts();
        List<FamilySubscriptionDTO> familySubscriptions = new ArrayList<>();

        List<Subscription> subscriptions = subscriptionRepository.findByFamilyAccountIdIn(activeAccountIds);

        // Group subscriptions by family account
        Map<Long, List<UserSubscriptionDTO>> familyAccountSubscriptions = new HashMap<>();
        
        for (Subscription subscription : subscriptions) {
            UserSubscriptionDTO userSubscription = new UserSubscriptionDTO();
            userSubscription.setUserId(subscription.getUserId());
            userSubscription.setPack(subscription.getPack());
            
            familyAccountSubscriptions
                .computeIfAbsent(subscription.getFamilyAccountId(), k -> new ArrayList<>())
                .add(userSubscription);
        }

        // Create FamilySubscriptionDTO objects and calculate the total bill amount
        for (Map.Entry<Long, List<UserSubscriptionDTO>> entry : familyAccountSubscriptions.entrySet()) {
            FamilySubscriptionDTO familySubscriptionDTO = new FamilySubscriptionDTO();
            familySubscriptionDTO.setFamilyAccountId(entry.getKey());
            familySubscriptionDTO.setUserSubscriptions(entry.getValue());

            // Calculate the total bill amount for this family account
            BigDecimal totalBillAmount = calculateTotalBillAmount(entry.getValue());
            familySubscriptionDTO.setTotalBillAmount(totalBillAmount);

            familySubscriptions.add(familySubscriptionDTO);
        }

        return familySubscriptions;
    }

    // Calculate the total bill amount for a list of user subscriptions
    private BigDecimal calculateTotalBillAmount(List<UserSubscriptionDTO> userSubscriptions) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (UserSubscriptionDTO userSubscription : userSubscriptions) {
            // Add the price of each subscribed pack to the total amount
            totalAmount = totalAmount.add(userSubscription.getPack().getPrice());
        }
        return totalAmount;
    }

}

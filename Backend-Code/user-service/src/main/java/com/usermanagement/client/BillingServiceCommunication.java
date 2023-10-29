package com.usermanagement.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.usermanagement.dto.SubscriptionDTO;

//Interface to establish communication with Billing-Service

@Service
@FeignClient(name = "billing-service")
public interface BillingServiceCommunication {

    @PostMapping("api/family-subscriptions/{familyAccountId}")
    List<SubscriptionDTO> getAccountSubscriptions(@PathVariable("familyAccountId") Long familyAccountId);
    
    @PostMapping("api/family-subscriptions/remove-user-subscriptions/{userId}")
    String removeUserSubscriptions(@PathVariable("userId") Long userId);
    
}
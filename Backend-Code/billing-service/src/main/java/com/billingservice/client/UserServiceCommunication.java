package com.billingservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//Interface to establish communication with User Service

@Service
@FeignClient(name = "user-service")
public interface UserServiceCommunication {

    @GetMapping("api/family-account/active-accounts")
    List<Long> getActiveAccounts();
    
    @PostMapping("api/family-account/deactivate-accounts")
    String deactivateAccounts();
}
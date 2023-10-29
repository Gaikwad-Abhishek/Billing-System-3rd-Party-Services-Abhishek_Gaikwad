package com.invoiceservice.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

//Interface to establish Communication between User Service

@Service
@FeignClient(name = "user-service")
public interface UserServiceCommunication {

    @GetMapping("api/family-account/primary-user-mail")
    String getPrimaryUserMail(Long familyAccountId);
    
}
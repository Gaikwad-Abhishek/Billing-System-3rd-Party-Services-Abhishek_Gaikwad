package com.collectionservice.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Interface to establish communication with User Service

@Service
@FeignClient(name = "user-service")
public interface UserServiceCommunication {

	@PostMapping("api/family-account/activate/{accountId}")
	String activeAccount(@PathVariable("accountId") Long accountId);
	
    @PostMapping("api/family-account/suspend")
    String suspendAccounts(List<Long> accountId);
    
    @PostMapping("api/family-account/terminate")
    String terminateAccounts(List<Long> accountId);
    
}
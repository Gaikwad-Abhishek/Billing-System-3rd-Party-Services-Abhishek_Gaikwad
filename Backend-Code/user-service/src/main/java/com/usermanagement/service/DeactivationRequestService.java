package com.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.DeactivationRequest;
import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.enums.FamilyAccountStatus;
import com.usermanagement.exception.FamilyAccountNotFoundException;
import com.usermanagement.repository.DeactivationRequestRepository;
import com.usermanagement.repository.FamilyAccountRepository;

@Service
public class DeactivationRequestService {
	
    @Autowired
    private DeactivationRequestRepository deactivationRequestRepository;
    
    @Autowired
    private FamilyAccountRepository familyAccountRepository;

    public String createDeactivationRequest(Long familyAccountId) {
    	FamilyAccount familyAccount = familyAccountRepository.findById(familyAccountId).orElseThrow(
				() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + familyAccountId));
        DeactivationRequest request = new DeactivationRequest();
        request.setFamilyAccountId(familyAccountId);
        deactivationRequestRepository.save(request);
        return "Deactivation Request Created";
    }
    
    public String deactivateAccounts() {
        
    	List<DeactivationRequest> deactivateAccounts = deactivationRequestRepository.findAll();
    	
    	for(DeactivationRequest deactivateAccount  : deactivateAccounts) {
    		FamilyAccount familyAccount = familyAccountRepository.findById(deactivateAccount.getFamilyAccountId()).orElseThrow(
    				() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " ));
    		familyAccount.setAccountStatus(FamilyAccountStatus.INACTIVE);
    		familyAccountRepository.save(familyAccount);
    	}
    	
    	deactivationRequestRepository.deleteAll();
    	return "Accounts Deactivated";
    }

    public DeactivationRequest getDeactivationRequestById(Long requestId) {
        return deactivationRequestRepository.findById(requestId).orElse(null);
    }

    // You can add more service methods as needed

    public void deleteDeactivationRequest(Long requestId) {
        deactivationRequestRepository.deleteById(requestId);
    }
}

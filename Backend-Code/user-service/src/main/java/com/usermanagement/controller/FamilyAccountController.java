package com.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.FamilyAccountDetailsDTO;
import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.service.DeactivationRequestService;
import com.usermanagement.service.FamilyAccountService;
import com.usermanagement.service.JwtService;
import com.usermanagement.service.OtpService;
//import com.usermanagement.service.MessageProducerService;
import com.usermanagement.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/family-account")
@Api(value = "Family API")
public class FamilyAccountController {

    @Autowired
    private FamilyAccountService familyAccountService; 

    @Autowired
    private UserService userService; 
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private DeactivationRequestService deactivationRequestService; 
    
    

    @ApiOperation(value = "Create Family Account", notes = "Create a new family account")
    @PostMapping("/create")
    public ResponseEntity<String> createFamilyAccount(@RequestHeader("Authorization")String jwtToken) {
    	Long userId = jwtService.getUserId(jwtToken);
        String response = familyAccountService.createFamilyAccount(userId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/details")
    public ResponseEntity<FamilyAccountDetailsDTO> getFamilyAccountDetails(@RequestHeader("Authorization")String jwtToken) {
    	Long userId = jwtService.getUserId(jwtToken);
    	return ResponseEntity.ok(familyAccountService.getFamilyAccountDetails(userId));
    }

    @ApiOperation(value = "Add Family Member to Account", notes = "Add a family member to an existing family account")
    @PostMapping("/add-member/{contactNo}/validate")
    public ResponseEntity<String> addFamilyMemberValidate(
    		@RequestHeader("Authorization")String jwtToken,
            @PathVariable String contactNo
    ) {
    	Long userId = jwtService.getUserId(jwtToken);
    	otpService.generateAndSendOtp(userId,contactNo);
        return ResponseEntity.ok("OTP generated Successfully");
    }
    
    @ApiOperation(value = "Add Family Member to Account", notes = "Add a family member to an existing family account")
    @PostMapping("/add-member/{contactNo}/{otp}")
    public ResponseEntity<String> addFamilyMember(
    		@RequestHeader("Authorization")String jwtToken,
            @PathVariable String contactNo,
            @PathVariable String otp
    ) {
    	Long userId = jwtService.getUserId(jwtToken);
        String response = familyAccountService.addFamilyMember(userId,otp,contactNo);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/remove-member/{userId}")
    public ResponseEntity<String> addFamilyMember(
            @PathVariable Long userId
    ) {
        String response = familyAccountService.removeFamilyMember(userId);
        return ResponseEntity.ok(response);
    }
    
    

    @ApiOperation(value = "Activate Family Account", notes = "Start the billing cycle for a family account")
    @PostMapping("/activate/{accountId}")
    public ResponseEntity<String> activateFamilyAccount(@PathVariable Long accountId) {
        FamilyAccount activatedAccount = familyAccountService.activateFamilyAccount(accountId);
        return ResponseEntity.ok("Account Activated");
    }
    
    @PostMapping("/deactivate-request/{familyAccountId}")
    public ResponseEntity<String> generateDeactivationRequest(
            @PathVariable Long familyAccountId
    ) {
        String response = deactivationRequestService.createDeactivationRequest(familyAccountId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/deactivate-accounts")
    public ResponseEntity<String> deactivateAccounts() {
        String response = deactivationRequestService.deactivateAccounts();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/suspend")
    public ResponseEntity<String> suspendFamilyAccount(@RequestBody List<Long> accountId) {
        System.out.println(accountId);
    	familyAccountService.suspendFamilyAccount(accountId);
        return ResponseEntity.ok("Accounts Suspended");
    }
    
    @PostMapping("/terminate")
    public ResponseEntity<String> terminateFamilyAccount(@RequestBody List<Long> accountId) {
        familyAccountService.terminateFamilyAccount(accountId);
        return ResponseEntity.ok("Accounts Terminated");
    }
    
    @PostMapping("/primary-user-mail")
    public ResponseEntity<String> getPrimaryUserMail(@RequestBody Long familyAccountId) {
        return ResponseEntity.ok(familyAccountService.getPrimaryUserMail(familyAccountId));
    }
    
    @GetMapping("/active-accounts")
    public ResponseEntity<List<Long>> getActiveAccounts() {
    	return ResponseEntity.ok(familyAccountService.getActiveFamilyAccountIds());
    }
    	
}

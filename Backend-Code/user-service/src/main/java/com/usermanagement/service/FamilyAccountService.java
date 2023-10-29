package com.usermanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.client.BillingServiceCommunication;
import com.usermanagement.dto.FamilyAccountDetailsDTO;
import com.usermanagement.dto.SubscriptionDTO;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.dto.UserSubscriptionDetailsDTO;
import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.entity.User;
import com.usermanagement.enums.FamilyAccountStatus;
import com.usermanagement.exception.ActiveFamilyAccountNotFoundException;
import com.usermanagement.exception.AlreadyMemberOfAnotherFamilyAccountException;
import com.usermanagement.exception.EmailNotFoundException;
import com.usermanagement.exception.FamilyAccountAlreadyExistsException;
import com.usermanagement.exception.FamilyAccountNotFoundException;
import com.usermanagement.exception.OTPValidationFailedException;
import com.usermanagement.exception.UserNotFoundException;
import com.usermanagement.mapper.UserMapper;
import com.usermanagement.repository.FamilyAccountRepository;
import com.usermanagement.repository.UserRepository;

@Service
public class FamilyAccountService {

	
	@Autowired
	private FamilyAccountRepository familyAccountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private BillingServiceCommunication billingServiceCommunication;

	public String createFamilyAccount(Long userId) {

		// Check if the user already has a FamilyAccount
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

		if (familyAccountRepository.findByPrimaryUser(user).isPresent()) {
			throw new FamilyAccountAlreadyExistsException("User with ID " + userId + " already has a FamilyAccount.");
		} else if (user.getFamilyAccount() != null) {
			throw new AlreadyMemberOfAnotherFamilyAccountException(
					"User with ID " + userId + " is a secondary user in an existing FamilyAccount.");
		}

		// If both conditions pass, create a new FamilyAccount
		FamilyAccount familyAccount = new FamilyAccount();
		familyAccount.setPrimaryUser(user);
		familyAccountRepository.save(familyAccount);
		
		return "Family Account Created Successfully"; 
	}
	
	public FamilyAccountDetailsDTO getFamilyAccountDetails(Long userId) {
		
		User primaryUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
		
		FamilyAccount familyAccount = familyAccountRepository.findByPrimaryUser(primaryUser).orElseThrow(() -> new FamilyAccountNotFoundException(
				"FamilyAccount not found "));
		
		Map<Long, List<SubscriptionDTO>> subscriptionsByUserId = getUserSubscription(familyAccount.getFamilyAccountId());
		
	    FamilyAccountDetailsDTO familyAccountDetailsDTO = new FamilyAccountDetailsDTO();
	    
	    // Map fields from familyAccount to familyAccountDetailsDTO
	    familyAccountDetailsDTO.setFamilyAccountId(familyAccount.getFamilyAccountId());
	    familyAccountDetailsDTO.setAccountStatus(familyAccount.getAccountStatus());
	    UserDTO primaryUserDTO = UserMapper.convertToDTO(primaryUser);

	    familyAccountDetailsDTO.setPrimaryUser(primaryUserDTO);
	    UserSubscriptionDetailsDTO priUserSubscriptionDetailsDTO = new UserSubscriptionDetailsDTO();

        priUserSubscriptionDetailsDTO.setUserId(primaryUser.getUserId());
        priUserSubscriptionDetailsDTO.setUsername(primaryUser.getUsername());
        
     // Fetch subscriptions from the existing map using the user's ID
        List<SubscriptionDTO> priSubscriptions = subscriptionsByUserId.get(priUserSubscriptionDetailsDTO.getUserId());
        priUserSubscriptionDetailsDTO.setSubscriptions(priSubscriptions);
        
	    // Map secondary users
	    List<UserSubscriptionDetailsDTO> secondaryUserDTOs = new ArrayList<>();
	    for (User secondaryUser : familyAccount.getSecondaryUsers()) {
	        UserSubscriptionDetailsDTO userSubscriptionDetailsDTO = new UserSubscriptionDetailsDTO();
	        userSubscriptionDetailsDTO.setUserId(secondaryUser.getUserId());
	        userSubscriptionDetailsDTO.setUsername(secondaryUser.getUsername());
	        userSubscriptionDetailsDTO.setFirstName(secondaryUser.getFirstName());
	        userSubscriptionDetailsDTO.setLastName(secondaryUser.getLastName());
	        
	        
	        // Fetch subscriptions from the existing map using the user's ID
	        List<SubscriptionDTO> subscriptions = subscriptionsByUserId.get(userSubscriptionDetailsDTO.getUserId());
	        
	        if (subscriptions != null) {
	            userSubscriptionDetailsDTO.setSubscriptions(subscriptions);
	        } else {
	            // Handle the case where no subscriptions are found for the user
	            userSubscriptionDetailsDTO.setSubscriptions(new ArrayList<>());
	        }
	        
	        secondaryUserDTOs.add(userSubscriptionDetailsDTO);
	    }
	    secondaryUserDTOs.add(priUserSubscriptionDetailsDTO);
	    familyAccountDetailsDTO.setSecondaryUsers(secondaryUserDTOs);

	    return familyAccountDetailsDTO;
	}

	public Map<Long, List<SubscriptionDTO>> getUserSubscription(Long familyAccountId){
		
		List<SubscriptionDTO> receivedSubscriptions = billingServiceCommunication.getAccountSubscriptions(familyAccountId);
		
		Map<Long, List<SubscriptionDTO>> subscriptionsByUserId = new HashMap<>();

	    // Group received subscriptions by user ID
	    for (SubscriptionDTO subscription : receivedSubscriptions) {
	        Long userId = subscription.getUserId();
	        subscriptionsByUserId
	            .computeIfAbsent(userId, k -> new ArrayList<>())
	            .add(subscription);
	    }
	    
	    return subscriptionsByUserId;
	}
	
	public String addFamilyMember(Long userId,String otp, String contactNo) {

		// Check if the family account exists
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

		FamilyAccount familyAccount = familyAccountRepository.findByPrimaryUser(user)
				.orElseThrow(() -> new FamilyAccountNotFoundException(
						"FamilyAccount not found for user with ID " + user.getUserId()));
		
		User familyMember = userRepository.findByContactNo(contactNo)
				.orElseThrow(() -> new UserNotFoundException("No user with contact " + contactNo));
		
		if (otpService.validateOTP(userId,otp) == false) {
			throw new OTPValidationFailedException("Invalid OTP. Please enter the correct OTP.");
		}
		if (familyMember.getFamilyAccount() != null) {
			throw new AlreadyMemberOfAnotherFamilyAccountException(
					"User with  " + contactNo + " is a secondary user in an existing FamilyAccount.");
		}

		// Add the family member to the account
		familyMember.setFamilyAccount(familyAccount);
		userRepository.save(familyMember); 
				
		return "User Added to Family Account";
	}
	
	public String removeFamilyMember(Long userId) {

		// Check if the family account exists
		User familyMember = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
		
		if (familyMember.getFamilyAccount() != null) {
			familyMember.setFamilyAccount(null);
		}

		userRepository.save(familyMember); 
		billingServiceCommunication.removeUserSubscriptions(userId);
		return "Member Removed Family Account";
	}

	public FamilyAccount getFamilyAccountById(Long accountId) {
		return familyAccountRepository.findById(accountId).orElseThrow(
				() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + accountId));
	}

	public FamilyAccount activateFamilyAccount(Long accountId) {

		FamilyAccount familyAccount = familyAccountRepository.findById(accountId).orElseThrow(
				() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + accountId));

		familyAccount.setAccountStatus(FamilyAccountStatus.ACTIVE);
		familyAccount = familyAccountRepository.save(familyAccount);

		return familyAccount;
	}

	public String suspendFamilyAccount(List<Long> accountIds) {

		for (Long accountId : accountIds) {
			FamilyAccount familyAccount = familyAccountRepository.findById(accountId).orElseThrow(
					() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + accountId));

			familyAccount.setAccountStatus(FamilyAccountStatus.SUSPENDED);
			familyAccountRepository.save(familyAccount);
		}

		return "Accounts Suspended";
	}

	public String terminateFamilyAccount(List<Long> accountIds) {

		for (Long accountId : accountIds) {
			FamilyAccount familyAccount = familyAccountRepository.findById(accountId).orElseThrow(
					() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + accountId));

				familyAccount.setAccountStatus(FamilyAccountStatus.TERMINATED);
				familyAccountRepository.save(familyAccount);
			}
		
	return "Accounts Terminated";

	}

	public List<Long> getActiveFamilyAccountIds() {

		List<FamilyAccount> activeFamilyAccounts = familyAccountRepository
				.findByAccountStatus(FamilyAccountStatus.ACTIVE).orElseThrow(
						() ->  new ActiveFamilyAccountNotFoundException("No active FamilyAccounts found."));
		
		List<Long> activeFamilyAccountIds = activeFamilyAccounts.stream()
				.map(FamilyAccount::getFamilyAccountId)
				.toList();

		return activeFamilyAccountIds;
	}

	public String getPrimaryUserMail(Long familyAccountId) {

		FamilyAccount familyAccount = familyAccountRepository.findById(familyAccountId).orElseThrow(
				() -> new FamilyAccountNotFoundException("FamilyAccount not found for user with ID " + familyAccountId));

			User primaryUser = familyAccount.getPrimaryUser();

			if (primaryUser != null) {
				if(primaryUser.getEmail() == null) {
					throw new EmailNotFoundException("Email not found for the primary user.");
				}
			}
				
		return primaryUser.getEmail();
		
	}

}

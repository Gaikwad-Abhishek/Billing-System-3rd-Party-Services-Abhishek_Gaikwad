package com.usermanagement.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usermanagement.entity.OTP;
import com.usermanagement.entity.User;
import com.usermanagement.exception.OTPNotFoundException;
import com.usermanagement.exception.UserNotFoundException;
import com.usermanagement.repository.OTPRepository;
import com.usermanagement.repository.UserRepository;

@Service
public class OtpService {
	
	@Autowired
	OTPRepository otpRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public String generateOtp() {
	    // Generate a random 6-digit OTP
	    int otpLength = 6;
	    StringBuilder otp = new StringBuilder();

	    for (int i = 0; i < otpLength; i++) {
	        int digit = (int) (Math.random() * 10);
	        otp.append(digit);
	    }

	    return otp.toString();
	}
	
    public void saveOTPForUser(Long userId, String otpValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        // Calculate the expiration time (15 minutes from the current time)
        Date expiryDate = new Date(System.currentTimeMillis() + 15 * 60 * 1000);

        OTP otp = new OTP(otpValue, expiryDate, user);
        otpRepository.save(otp);
    }
    
    public boolean validateOTP(Long userId, String enteredOTP) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));

        OTP otp = otpRepository.findByUser(user).orElseThrow(() -> new OTPNotFoundException("OTP not generated. Try Again"));
        otpRepository.delete(otp);        
        
        return enteredOTP.equals(otp.getOtpValue());
    }
    
    public boolean sendOtp(String otp,String contactNo) {
    	
    	//Currently printing on console 
    	System.out.println(otp);
    	return true;
    	
    }
    
    public boolean generateAndSendOtp(Long userId, String contactNo) {
    	
    	String otp = generateOtp();
    	saveOTPForUser(userId,otp);
    	sendOtp(otp,contactNo);
    	
    	return true;
    }
    
}

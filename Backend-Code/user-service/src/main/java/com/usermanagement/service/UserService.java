package com.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.dto.UserRegistrationDTO;
import com.usermanagement.entity.User;
import com.usermanagement.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FamilyAccountService familyAccountService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,FamilyAccountService familyAccountService) {
        this.userRepository = userRepository;
        this.familyAccountService = familyAccountService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }
    
    public void registerUser(UserRegistrationDTO registrationDTO) {
        // Perform attribute validation as needed
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new RuntimeException("Username is already in use.");
        }else if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email is already registered.");
        }else if (userRepository.existsByEmail(registrationDTO.getContactNo())) {
        	throw new RuntimeException("Mobile Number is already registered.");
        }
        
        String email = registrationDTO.getEmail();
        if (!email.endsWith("@gmail.com")) {
            throw new RuntimeException("Invalid email format. Please use an email ending with '@gmail.com'.");
        }

        // Create a new User entity and save it to the database
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
//        user.setPassword(registrationDTO.getPassword());
        user.setPassword("{bcrypt}" + passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setContactNo(registrationDTO.getContactNo());
        userRepository.save(user);
    }

    
}


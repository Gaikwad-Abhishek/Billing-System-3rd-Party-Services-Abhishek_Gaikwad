package com.usermanagement.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
    	var myUserEntity = userService.findByUsername(username);

        var springSecurityUserEntity = User.withUsername(myUserEntity.getUsername())
                                            .password(
                                            myUserEntity.getPassword())
                                            .build();
        return springSecurityUserEntity;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
         PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
         return encoder;
    }
    
}
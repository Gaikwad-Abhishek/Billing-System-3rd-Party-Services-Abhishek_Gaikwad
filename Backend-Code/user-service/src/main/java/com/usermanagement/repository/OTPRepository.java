package com.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.OTP;
import com.usermanagement.entity.User;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
	
    Optional<OTP> findByUser(User user);

}

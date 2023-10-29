package com.usermanagement.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.entity.User;
import com.usermanagement.enums.FamilyAccountStatus;

@Repository
public interface FamilyAccountRepository extends JpaRepository<FamilyAccount, Long> {
	
	Optional<FamilyAccount> findByPrimaryUser(User user);
	
	Optional<List<FamilyAccount>> findByAccountStatus(FamilyAccountStatus familyAccountStatus);
}


package com.collectionservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collectionservice.entity.SuspendedAccount;

@Repository
public interface SuspendedAccountRepository extends JpaRepository<SuspendedAccount, Long> {
	
	boolean existsByAccountIdsContaining(Long accountId);
	
	Optional<SuspendedAccount> findTopByOrderByIdDesc();
}


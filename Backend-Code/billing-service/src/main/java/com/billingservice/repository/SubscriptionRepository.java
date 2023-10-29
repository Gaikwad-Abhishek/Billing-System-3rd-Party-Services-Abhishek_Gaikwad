package com.billingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.billingservice.entity.Subscription;

import jakarta.transaction.Transactional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
	List<Subscription> findByFamilyAccountIdIn(List<Long> familyAccountIds);
	
	List<Subscription> findByFamilyAccountId(Long familyAccountId);
	
	@Modifying
	@Transactional
	void deleteAllByUserId(Long userId);
	
    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.userId = ?1 AND s.familyAccountId = ?2 AND s.pack = ?3")
    void deleteByUserIdAndFamilyAccountIdAndPack(Long userId, Long familyAccountId, Long packId);

}

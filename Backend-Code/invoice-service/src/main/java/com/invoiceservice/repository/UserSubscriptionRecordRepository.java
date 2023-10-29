package com.invoiceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.invoiceservice.entity.UserSubscriptionRecord;

@Repository
public interface UserSubscriptionRecordRepository extends JpaRepository<UserSubscriptionRecord, Long> {
    
}

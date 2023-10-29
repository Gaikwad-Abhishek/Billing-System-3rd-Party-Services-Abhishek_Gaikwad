package com.collectionservice.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.collectionservice.entity.CollectionCycleDetail;
import com.collectionservice.repository.CollectionCycleDetailRepository;

@Service

public class CollectionScheduler {
	
	@Autowired
	AccountStatusUpdateService accountStatusUpdateService;
	
	@Autowired
	CollectionCycleDetailRepository collectionCycleDetailRepository;
	
//	@Scheduled(cron = "0 0 0 * * ?")
//	@Scheduled(fixedRate = 10000)
	public void runCollectionService() {
		
		LocalDate today = LocalDate.now(); // Get the current date

        // Fetch the latest collection cycle detail
        Optional<CollectionCycleDetail> latestDetailOptional = collectionCycleDetailRepository.findTopByOrderByStartDateDesc();

        if (latestDetailOptional.isPresent()) {
            CollectionCycleDetail latestDetail = latestDetailOptional.get();

            if (latestDetail.getSuspensionDate() != null && latestDetail.getSuspensionDate().equals(today)) {
            	accountStatusUpdateService.suspendAccounts();
            }

            if (latestDetail.getTerminationDate() != null && latestDetail.getTerminationDate().equals(today)) {
            	accountStatusUpdateService.terminateAccounts();
            }
        }
        
	}
}

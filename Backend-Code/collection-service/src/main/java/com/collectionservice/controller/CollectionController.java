package com.collectionservice.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collectionservice.clients.InvoiceServiceCommunication;
import com.collectionservice.service.AccountStatusUpdateService;
import com.collectionservice.service.CollectionCycleDetailService;
import com.collectionservice.service.PaymentService;

@RestController
@RequestMapping("api/collection")
public class CollectionController {

	@Autowired
	private InvoiceServiceCommunication invoiceServiceCommunication;

	@Autowired
	private AccountStatusUpdateService accountStatusUpdateService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private CollectionCycleDetailService collectionCycleDetailService;
	

	@GetMapping("/trigger-suspension")
	public String executeAccountSuspension() {
		accountStatusUpdateService.suspendAccounts();
		return "Suspension Cycle Executed";
	}
	
	@GetMapping("/trigger-termination")
	public String executeAccountTermination() {
		accountStatusUpdateService.terminateAccounts();
		return "Termination Cycle Executed";
	}
		
	@PostMapping("/initiate-collection-cycle")
	public ResponseEntity<String> initiateCollectionCycle() {
		LocalDate startDate = LocalDate.now();
		collectionCycleDetailService.createCollectionCycleDetail(startDate, startDate.plus(14, ChronoUnit.DAYS), startDate.plus(28, ChronoUnit.DAYS));
		return ResponseEntity.ok("Collection Cycle Initiated");
	}

	@PostMapping("family-account/{familyAccountId}/invoice/{invoiceId}/pay")
	public ResponseEntity<String> payment(@PathVariable Long familyAccountId,@PathVariable Long invoiceId) {
		paymentService.paymentReceived(familyAccountId, invoiceId);
		return ResponseEntity.ok("Payment Received");
	}

}

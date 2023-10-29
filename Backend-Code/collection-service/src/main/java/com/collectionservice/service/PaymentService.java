package com.collectionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collectionservice.clients.InvoiceServiceCommunication;
import com.collectionservice.clients.UserServiceCommunication;

@Service
public class PaymentService {
	
	@Autowired
	private SuspendedAccountService suspendedAccountService;
	
	@Autowired
	private UserServiceCommunication userServiceCommunication;
	
	@Autowired
	private InvoiceServiceCommunication invoiceServiceCommunication;
	
	public String paymentReceived(Long familyAccountId, Long invoiceId) {
		
		if (suspendedAccountService.isAccountSuspended(familyAccountId)) {
			userServiceCommunication.activeAccount(familyAccountId);
		}
		
		invoiceServiceCommunication.updateInvoiceStatus(invoiceId);
		
		return "Account Status Updated";
	}
}

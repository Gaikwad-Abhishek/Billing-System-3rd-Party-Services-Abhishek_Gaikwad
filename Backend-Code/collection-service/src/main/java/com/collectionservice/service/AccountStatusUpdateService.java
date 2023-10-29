package com.collectionservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collectionservice.clients.InvoiceServiceCommunication;
import com.collectionservice.clients.UserServiceCommunication;
import com.collectionservice.entity.CollectionCycleDetail;
import com.collectionservice.exception.NoCollectionRecordFound;
import com.collectionservice.repository.CollectionCycleDetailRepository;

@Service
public class AccountStatusUpdateService {

	@Autowired
	private UserServiceCommunication userServiceCommunication;

	@Autowired
	private InvoiceServiceCommunication invoiceServiceCommunication;

	@Autowired
	private SuspendedAccountService suspendedAccountService;

	@Autowired
	private CollectionCycleDetailRepository collectionCycleDetailRepository;

	public String suspendAccounts() {

		List<Long> suspendAccount = getOverdueAccounts();
		userServiceCommunication.suspendAccounts(suspendAccount);
		suspendedAccountService.addAccountsToSuspendedList(suspendAccount);
		return "Accounts Suspended";

	}

	public String terminateAccounts() {

		List<Long> terminateAccount = getOverdueAccounts();
		userServiceCommunication.terminateAccounts(terminateAccount);
		suspendedAccountService.removeSuspendedAccounts();
		return "Accounts Terminated";
	}

	public List<Long> getOverdueAccounts() {

		Optional<CollectionCycleDetail> latestDetailOptional = collectionCycleDetailRepository
				.findTopByOrderByStartDateDesc();

		if (latestDetailOptional.isPresent()) {
			CollectionCycleDetail latestDetail = latestDetailOptional.get();
			LocalDate invoiceDate = latestDetail.getStartDate();
			return invoiceServiceCommunication.getOverdueAccounts(invoiceDate);
		}else {
			throw new NoCollectionRecordFound("No collection record found");
	    }

	}
}

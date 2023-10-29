package com.collectionservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collectionservice.entity.SuspendedAccount;
import com.collectionservice.exception.SuspendedAccountNotFoundException;
import com.collectionservice.repository.SuspendedAccountRepository;

@Service
public class SuspendedAccountService {

	@Autowired
	private SuspendedAccountRepository suspendedAccountRepository;

	public boolean isAccountSuspended(Long accountId) {
		return suspendedAccountRepository.existsByAccountIdsContaining(accountId);
	}

	public SuspendedAccount addAccountsToSuspendedList(List<Long> accountIds) {
		// Get the last database entry of SuspendedAccounts
		Optional<SuspendedAccount> suspendedAccountOpt = suspendedAccountRepository.findTopByOrderByIdDesc();
		SuspendedAccount suspendedAccount;
		if (suspendedAccountOpt.isPresent()) {
			suspendedAccount = suspendedAccountOpt.get();
			List<Long> existingAccountIds = suspendedAccount.getAccountIds();
			existingAccountIds.addAll(accountIds);
			suspendedAccount.setAccountIds(existingAccountIds);
		} else {
			suspendedAccount = new SuspendedAccount(accountIds);
		}

		return suspendedAccountRepository.save(suspendedAccount);
	}

	public SuspendedAccount removeAccountFromSuspendedList(Long accountId) {

		SuspendedAccount suspendedAccount = suspendedAccountRepository.findTopByOrderByIdDesc()
				.orElseThrow(() -> new SuspendedAccountNotFoundException("No suspended account found."));

		List<Long> accountIds = suspendedAccount.getAccountIds();
		accountIds.remove(accountId);
		suspendedAccount.setAccountIds(accountIds);
		return suspendedAccountRepository.save(suspendedAccount);

	}

	public SuspendedAccount removeSuspendedAccounts() {

		SuspendedAccount suspendedAccounts = suspendedAccountRepository.findTopByOrderByIdDesc()
				.orElseThrow(() -> new SuspendedAccountNotFoundException("No suspended account found."));
		
		suspendedAccounts.setAccountIds(new ArrayList<>());
		return suspendedAccountRepository.save(suspendedAccounts);

	}
}

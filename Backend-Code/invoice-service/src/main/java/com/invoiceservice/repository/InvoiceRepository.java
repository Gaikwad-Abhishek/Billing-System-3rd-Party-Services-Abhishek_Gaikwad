package com.invoiceservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.invoiceservice.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
	List<Invoice> findByFamilyAccountIdOrderByInvoiceDateDesc(Long familyAccountId);
	
	 @Query("SELECT DISTINCT i.familyAccountId FROM Invoice i " +
	        "WHERE i.invoiceDate = :generationDate AND i.paymentStatus = 'UNPAID'")
	 List<Long> findFamilyAccountIdsByDateAndPaymentStatus(LocalDate generationDate);
}

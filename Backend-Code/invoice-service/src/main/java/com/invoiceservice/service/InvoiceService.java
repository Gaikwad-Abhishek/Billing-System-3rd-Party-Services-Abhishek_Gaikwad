package com.invoiceservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceservice.DTO.FamilySubscriptionDTO;
import com.invoiceservice.DTO.UserSubscriptionDTO;
import com.invoiceservice.entity.Invoice;
import com.invoiceservice.entity.UserSubscriptionRecord;
import com.invoiceservice.enums.PaymentStatus;
import com.invoiceservice.exception.InvoiceNotFoundException;
import com.invoiceservice.repository.InvoiceRepository;
import com.invoiceservice.repository.UserSubscriptionRecordRepository;


@Service
public class InvoiceService {
	
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserSubscriptionRecordRepository userSubscriptionRecordRepository;

    public String createInvoices(List<FamilySubscriptionDTO> familySubscriptions) {
    	
        for (FamilySubscriptionDTO familySubscription : familySubscriptions) {
            Invoice invoice = new Invoice();
            invoice.setFamilyAccountId(familySubscription.getFamilyAccountId());
            invoice.setTotalAmount(familySubscription.getTotalBillAmount());
            invoice.setInvoiceDate(LocalDate.now());

            List<UserSubscriptionRecord> userSubscriptions = new ArrayList<>();
            
            for (UserSubscriptionDTO userSubscriptionDTO : familySubscription.getUserSubscriptions()) {
                UserSubscriptionRecord userSubscriptionRecord = new UserSubscriptionRecord();
                userSubscriptionRecord.setUserId(userSubscriptionDTO.getUserId());
                userSubscriptionRecord.setPackId(userSubscriptionDTO.getPack().getId());
                userSubscriptionRecord.setProviderName(userSubscriptionDTO.getPack().getProviderName());
                userSubscriptionRecord.setPrice(userSubscriptionDTO.getPack().getPrice());
                userSubscriptionRecord.setInvoice(invoice);  // Associate with the invoice
                userSubscriptions.add(userSubscriptionRecord);

            }


            invoice.setUserSubscriptions(userSubscriptions);

            invoiceRepository.save(invoice);
            for (UserSubscriptionRecord userSubscription : userSubscriptions) {
            	userSubscriptionRecordRepository.save(userSubscription);
            }
        }
        return "Invoice Generated Successfully";
    }
    
    public List<Invoice> getUserInvoice(Long familyAccountId){
    	List<Invoice> userInvoices = invoiceRepository.findByFamilyAccountIdOrderByInvoiceDateDesc(familyAccountId);
    	return userInvoices;
    }
    
    public List<Long> getOverdueAccounts(LocalDate generationDate){
    	return invoiceRepository.findFamilyAccountIdsByDateAndPaymentStatus(generationDate);
    }
    
    public Invoice markInvoiceAsPaid(Long invoiceId) {
    	
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new InvoiceNotFoundException("Invoice with ID " + invoiceId + " not found"));
        invoice.setPaymentStatus(PaymentStatus.PAID);
        
        return invoiceRepository.save(invoice);
    }
}

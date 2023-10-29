package com.invoiceservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceservice.DTO.FamilySubscriptionDTO;
import com.invoiceservice.entity.Invoice;
import com.invoiceservice.service.EmailService;
import com.invoiceservice.service.InvoiceService;



@RestController
@RequestMapping("api/invoice")
public class InvoiceController {
    
	@Autowired
    private InvoiceService invoiceService;
	
	@Autowired 
	private EmailService emailService;
	

    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoices(@RequestBody List<FamilySubscriptionDTO> familySubscriptionDTO) {
    	  String response = invoiceService.createInvoices(familySubscriptionDTO);  
          return ResponseEntity.ok(response);
    }
    
    @PostMapping("/send-invoice-email")
    public ResponseEntity<String> generateInvoiceEmail(@RequestBody List<FamilySubscriptionDTO> familySubscriptionDTO) {  
    	  String response = emailService.generateEmailData(familySubscriptionDTO);
          return ResponseEntity.ok(response);
    }
    
    @GetMapping("/family-account/{accountId}")
    public ResponseEntity<List<Invoice>> getInvoices(@PathVariable Long accountId) {
    	return ResponseEntity.ok(invoiceService.getUserInvoice(accountId));
    }
    
    @PostMapping("/overdue-accounts")
    public ResponseEntity<List<Long>> processDate(@RequestBody LocalDate generationDate) {
        return ResponseEntity.ok(invoiceService.getOverdueAccounts(generationDate));
    }
    
    @PostMapping("/{invoiceId}/paid")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable Long invoiceId) {
        invoiceService.markInvoiceAsPaid(invoiceId);
    	return ResponseEntity.ok("Invoice Status Updated");
    }
    
//    @GetMapping("/send-mail")
//    public String sendMail() {
//    	try {
//    	emailService.sendInvoiceEmail("abhishekg0910@gmail.com",new BigDecimal("100"));
//    	}catch(Exception e) {
//    	}
//    	return "Mail Sent";
//    }
}

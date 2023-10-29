package com.collectionservice.clients;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//Interface to establish communication with Invoice Service

@Service
@FeignClient(name = "invoice-service")
public interface InvoiceServiceCommunication {

    @PostMapping("api/invoice/overdue-accounts")
    List<Long> getOverdueAccounts(LocalDate invoiceDate);
    
    @PostMapping("api/invoice/{invoiceId}/paid")
    String updateInvoiceStatus(@PathVariable("invoiceId") Long invoicetId);
    
    
    
}
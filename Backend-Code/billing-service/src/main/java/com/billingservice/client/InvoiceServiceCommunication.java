package com.billingservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.billingservice.DTO.FamilySubscriptionDTO;

//Interface to establish communication with Invoice Service

@Service
@FeignClient(name = "invoice-service")
public interface InvoiceServiceCommunication {

    @PostMapping("api/invoice/generate")
    String generateInvoice(List<FamilySubscriptionDTO> familySubscriptionDTO);
    
    @PostMapping("api/invoice/send-invoice-email")
    String generateInvoiceEmail(List<FamilySubscriptionDTO> familySubscriptionDTO);
    
}

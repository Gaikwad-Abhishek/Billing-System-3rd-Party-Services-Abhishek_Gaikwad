package com.invoiceservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.invoiceservice.DTO.FamilySubscriptionDTO;
import com.invoiceservice.clients.UserServiceCommunication;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    UserServiceCommunication userServiceCommunication;
    
    
    public String generateEmailData(List<FamilySubscriptionDTO> familySubscriptions) {
    	
    	for (FamilySubscriptionDTO familySubscription : familySubscriptions) {
             
            sendInvoiceEmail(userServiceCommunication.getPrimaryUserMail(familySubscription.getFamilyAccountId()), familySubscription.getTotalBillAmount());
    	}
    	
    	return "Email Process Initiated Successful";
    }
    
    public String sendInvoiceEmail(String to, BigDecimal amount) {
    	
    	try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            //Thymeleaf context and set variables for template rendering
            Context context = new Context();
            context.setVariable("invoiceDate", LocalDate.now());
            context.setVariable("dueDate", LocalDate.now().plusDays(14));
            context.setVariable("Amount", amount);

            // Process the Thymeleaf template with the context
            String emailContent = templateEngine.process("email-template", context);

            // Set email properties and send the email
            helper.setTo(to);
            helper.setSubject("Invoice");
            helper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            // Handle exceptions
        }
    	
    	return "Invoice Email Sent Successfully";

    }
}

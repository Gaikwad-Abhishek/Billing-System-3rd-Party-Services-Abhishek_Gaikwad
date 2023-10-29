package com.usermanagement.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usermanagement.controller.FamilyAccountController;
import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.entity.User;
import com.usermanagement.service.FamilyAccountService;
import com.usermanagement.service.JwtService;
import com.usermanagement.service.UserService;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// public class FamilyAccountControllerIntegrationTest {

//     @LocalServerPort
//     private int port;

//     @Autowired
//     private TestRestTemplate restTemplate;

//     @BeforeEach
//     public void setUp() {
//         restTemplate = restTemplate.withBasicAuth("", "");
//     }

//     @Test
//     public void testCreateFamilyAccount() {
//         // Create the request headers with a valid JWT token
//         HttpHeaders headers = new HttpHeaders();
//         headers.add("Authorization", "JWTTokenHere");

//         ResponseEntity<FamilyAccount> response = restTemplate.postForEntity(
//                 "http://localhost:" + port + "/api/family-account/create",
//                 headers, FamilyAccount.class);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }

//     @Test
//     public void testAddFamilyMember() {
//         // Create the request headers with a valid JWT token
//         HttpHeaders headers = new HttpHeaders();
//         headers.add("Authorization", "JWTTokenHere");

//         ResponseEntity<User> response = restTemplate.postForEntity(
//                 "http://localhost:" + port + "/api/family-account/add-member/123456789",
//                 headers, User.class);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }

//     @Test
//     public void testActivateFamilyAccount() {
//         // Create the request headers with a valid JWT token
//         HttpHeaders headers = new HttpHeaders();
//         headers.add("Authorization", "JWTTokenHere");

//         ResponseEntity<FamilyAccount> response = restTemplate.postForEntity(
//                 "http://localhost:" + port + "/api/family-account/activate/1",
//                 headers, FamilyAccount.class);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//     }
// }
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class FamilyAccountControllerIntegrationTest {
//
//    @InjectMocks
//    private FamilyAccountController familyAccountController;
//
//    @Mock
//    private FamilyAccountService familyAccountService;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JwtService jwtService;
//
//    @Test
//    public void testCreateFamilyAccount() {
//        // Mock JwtService to return a userId
//        when(jwtService.getUserId("JWTTokenHere")).thenReturn(1L);
//
//        // Mock FamilyAccountService to return a FamilyAccount instance
//        FamilyAccount familyAccount = new FamilyAccount();
//        when(familyAccountService.createFamilyAccount(1L)).thenReturn(familyAccount);
//
//        // Call the controller method
//        ResponseEntity<FamilyAccount> response = familyAccountController.createFamilyAccount("JWTTokenHere");
//
//        // Verify the response and interactions
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(familyAccount, response.getBody());
//        verify(jwtService, times(1)).getUserId("JWTTokenHere");
//        verify(familyAccountService, times(1)).createFamilyAccount(1L);
//    }
//
//    @Test
//    public void testAddFamilyMember() {
//        // Mock JwtService to return a userId
//        when(jwtService.getUserId("JWTTokenHere")).thenReturn(1L);
//
//        // Mock FamilyAccountService to return a User instance
//        User user = new User();
//        when(familyAccountService.addFamilyMember(1L, "123456789")).thenReturn(user);
//
//        // Call the controller method
//        ResponseEntity<User> response = familyAccountController.addFamilyMember("JWTTokenHere", "123456789");
//
//        // Verify the response and interactions
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//        verify(jwtService, times(1)).getUserId("JWTTokenHere");
//        verify(familyAccountService, times(1)).addFamilyMember(1L, "123456789");
//    }
//
//    @Test
//    public void testActivateFamilyAccount() {
//        when(familyAccountService.activateFamilyAccount(1L)).thenReturn(new FamilyAccount());
//
//        ResponseEntity<String> response = familyAccountController.activateFamilyAccount(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Account Activated", response.getBody());
//        verify(familyAccountService, times(1)).activateFamilyAccount(1L);
//    }
//}

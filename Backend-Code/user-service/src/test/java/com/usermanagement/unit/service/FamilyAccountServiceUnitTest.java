package com.usermanagement.unit.service;

//import com.usermanagement.entity.FamilyAccount;
//import com.usermanagement.entity.User;
//import com.usermanagement.enums.FamilyAccountStatus;
//import com.usermanagement.exception.*;
//import com.usermanagement.repository.FamilyAccountRepository;
//import com.usermanagement.repository.UserRepository;
//import com.usermanagement.service.FamilyAccountService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class FamilyAccountServiceUnitTest {
//
//    @InjectMocks
//    private FamilyAccountService familyAccountService;
//
//    @Mock
//    private FamilyAccountRepository familyAccountRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    public void testCreateFamilyAccount_Success() {
//         // Mock UserRepository to return a user with no family account
//         User user = new User();
//         user.setUserId(1L);
//         when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//         // Mock FamilyAccountRepository to save a family account
//         when(familyAccountRepository.save(any(FamilyAccount.class))).thenReturn(new FamilyAccount());
//
//         FamilyAccount familyAccount = familyAccountService.createFamilyAccount(1L);
//
//         assertNotNull(familyAccount);
//         verify(userRepository, times(1)).findById(1L);
//         verify(familyAccountRepository, times(1)).save(any(FamilyAccount.class));
//     }
//
//    @Test
//    public void testCreateFamilyAccount_UserAlreadyHasFamilyAccount() {
//        // Mock UserRepository to return a user with an existing family account
//        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
//        when(familyAccountRepository.findByPrimaryUser(any(User.class))).thenReturn(Optional.of(new FamilyAccount()));
//
//        assertThrows(FamilyAccountAlreadyExistsException.class, () -> {
//            familyAccountService.createFamilyAccount(1L);
//        });
//
//        verify(userRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, never()).save(any(FamilyAccount.class));
//    }
//
//    // Similarly, you can write tests for other methods of the service.
//
//    @Test
//    public void testGetActiveFamilyAccountIds_Success() {
//        // Mock FamilyAccountRepository to return active family accounts
//        when(familyAccountRepository.findByAccountStatus(FamilyAccountStatus.ACTIVE))
//                .thenReturn(Optional.of(Arrays.asList(new FamilyAccount(), new FamilyAccount())));
//
//        List<Long> activeFamilyAccountIds = familyAccountService.getActiveFamilyAccountIds();
//
//        assertEquals(2, activeFamilyAccountIds.size());
//        verify(familyAccountRepository, times(1)).findByAccountStatus(FamilyAccountStatus.ACTIVE);
//    }
//
//    @Test
//    public void testGetActiveFamilyAccountIds_NoActiveAccountsFound() {
//        // Mock FamilyAccountRepository to return no active family accounts
//        when(familyAccountRepository.findByAccountStatus(FamilyAccountStatus.ACTIVE)).thenReturn(Optional.empty());
//
//        assertThrows(ActiveFamilyAccountNotFoundException.class, () -> {
//            familyAccountService.getActiveFamilyAccountIds();
//        });
//
//        verify(familyAccountRepository, times(1)).findByAccountStatus(FamilyAccountStatus.ACTIVE);
//    }
//
//    // @Test
//    // public void testGetPrimaryUserMail_Success() {
//    //     // Mock FamilyAccountRepository to return a family account
//    //     FamilyAccount familyAccount = new FamilyAccount();
//    //     when(familyAccountRepository.findById(1L)).thenReturn(Optional.of(familyAccount));
//
//    //     // Mock UserRepository to return a primary user with an email
//    //     User primaryUser = new User();
//    //     primaryUser.setEmail("user@example.com");
//    //     familyAccount.setPrimaryUser(primaryUser);
//    //     when(userRepository.findById(primaryUser.getUserId())).thenReturn(Optional.of(primaryUser));
//
//    //     String email = familyAccountService.getPrimaryUserMail(1L);
//
//    //     assertEquals("user@example.com", email);
//    //     verify(familyAccountRepository, times(1)).findById(1L);
//    //     verify(userRepository, times(1)).findById(primaryUser.getUserId());
//    // }
//
//    @Test
//    public void testGetPrimaryUserMail_NoFamilyAccountFound() {
//        // Mock FamilyAccountRepository to return no family account
//        when(familyAccountRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(FamilyAccountNotFoundException.class, () -> {
//            familyAccountService.getPrimaryUserMail(1L);
//        });
//
//        verify(familyAccountRepository, times(1)).findById(1L);
//        verify(userRepository, never()).findById(any(Long.class));
//    }
//
//    // @Test
//    // public void testAddFamilyMember_Success() {
//    //     // Mock UserRepository to return a user
//    //     when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
//
//    //     // Mock FamilyAccountRepository to return a family account
//    //     when(familyAccountRepository.findByPrimaryUser(any(User.class))).thenReturn(Optional.of(new FamilyAccount()));
//
//    //     // Mock UserRepository to return a family member user
//    //     when(userRepository.findByUsername("contactNo")).thenReturn(Optional.of(new User()));
//
//    //     User addedMember = familyAccountService.addFamilyMember(1L, "contactNo");
//
//    //     assertNotNull(addedMember);
//    //     verify(userRepository, times(1)).findById(1L);
//    //     verify(familyAccountRepository, times(1)).findByPrimaryUser(any(User.class));
//    //     verify(userRepository, times(1)).findByUsername("contactNo");
//    //     verify(userRepository, times(1)).save(any(User.class));
//    // }
//
//    @Test
//    public void testAddFamilyMember_FamilyMemberAlreadyInAnotherAccount() {
//        // Mock UserRepository to return a user
//        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
//
//        // Mock FamilyAccountRepository to return a family account
//        when(familyAccountRepository.findByPrimaryUser(any(User.class))).thenReturn(Optional.of(new FamilyAccount()));
//
//        // Mock UserRepository to return a family member user with an existing family account
//        User familyMember = new User();
//        familyMember.setFamilyAccount(new FamilyAccount());
//        when(userRepository.findByUsername("contactNo")).thenReturn(Optional.of(familyMember));
//
//        assertThrows(AlreadyMemberOfAnotherFamilyAccountException.class, () -> {
//            familyAccountService.addFamilyMember(1L, "contactNo");
//        });
//
//        verify(userRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, times(1)).findByPrimaryUser(any(User.class));
//        verify(userRepository, times(1)).findByUsername("contactNo");
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    public void testAddFamilyMember_UserNotFound() {
//        // Mock UserRepository to return no user
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> {
//            familyAccountService.addFamilyMember(1L, "contactNo");
//        });
//
//        verify(userRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, never()).findByPrimaryUser(any(User.class));
//        verify(userRepository, never()).findByUsername("contactNo");
//        verify(userRepository, never()).save(any(User.class));
//    }
//
//    // @Test
//    // public void testActivateFamilyAccount_Success() {
//    //     // Mock FamilyAccountRepository to return a family account
//    //     when(familyAccountRepository.findById(1L)).thenReturn(Optional.of(new FamilyAccount()));
//
//    //     FamilyAccount activatedAccount = familyAccountService.activateFamilyAccount(1L);
//
//    //     assertNotNull(activatedAccount);
//    //     assertEquals(FamilyAccountStatus.ACTIVE, activatedAccount.getAccountStatus());
//    //     verify(familyAccountRepository, times(1)).findById(1L);
//    //     verify(familyAccountRepository, times(1)).save(any(FamilyAccount.class));
//    // }
//
//    @Test
//    public void testActivateFamilyAccount_FamilyAccountNotFound() {
//        // Mock FamilyAccountRepository to return no family account
//        when(familyAccountRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(FamilyAccountNotFoundException.class, () -> {
//            familyAccountService.activateFamilyAccount(1L);
//        });
//
//        verify(familyAccountRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, never()).save(any(FamilyAccount.class));
//    }
//
//    @Test
//    public void testSuspendFamilyAccount_Success() {
//        List<Long> accountIds = Arrays.asList(1L, 2L);
//
//        // Mock FamilyAccountRepository to return family accounts
//        when(familyAccountRepository.findById(1L)).thenReturn(Optional.of(new FamilyAccount()));
//        when(familyAccountRepository.findById(2L)).thenReturn(Optional.of(new FamilyAccount()));
//
//        String result = familyAccountService.suspendFamilyAccount(accountIds);
//
//        assertEquals("Accounts Suspended", result);
//        verify(familyAccountRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, times(1)).findById(2L);
//        verify(familyAccountRepository, times(2)).save(any(FamilyAccount.class));
//    }
//
//    @Test
//    public void testTerminateFamilyAccount_Success() {
//        List<Long> accountIds = Arrays.asList(1L, 2L);
//
//        // Mock FamilyAccountRepository to return family accounts
//        when(familyAccountRepository.findById(1L)).thenReturn(Optional.of(new FamilyAccount()));
//        when(familyAccountRepository.findById(2L)).thenReturn(Optional.of(new FamilyAccount()));
//
//        String result = familyAccountService.terminateFamilyAccount(accountIds);
//
//        assertEquals("Accounts Terminated", result);
//        verify(familyAccountRepository, times(1)).findById(1L);
//        verify(familyAccountRepository, times(1)).findById(2L);
//        verify(familyAccountRepository, times(2)).save(any(FamilyAccount.class));
//    }
//
//    // @Test
//    // public void testTerminateFamilyAccount_FamilyAccountNotFound() {
//    //     List<Long> accountIds = Arrays.asList(1L, 2L);
//
//    //     // Mock FamilyAccountRepository to return no family accounts
//    //     when(familyAccountRepository.findById(1L)).thenReturn(Optional.empty());
//    //     when(familyAccountRepository.findById(2L)).thenReturn(Optional.of(new FamilyAccount()));
//
//    //     assertThrows(FamilyAccountNotFoundException.class, () -> {
//    //         familyAccountService.terminateFamilyAccount(accountIds);
//    //     });
//
//    //     verify(familyAccountRepository, times(1)).findById(1L);
//    //     verify(familyAccountRepository, times(1)).findById(2L);
//    //     verify(familyAccountRepository, never()).save(any(FamilyAccount.class));
//    // }
//
//    // @Test
//    // public void testTerminateFamilyAccount_PartialFailure() {
//    //     List<Long> accountIds = Arrays.asList(1L, 2L, 3L);
//
//    //     // Mock FamilyAccountRepository to return family accounts
//    //     when(familyAccountRepository.findById(1L)).thenReturn(Optional.of(new FamilyAccount()));
//    //     when(familyAccountRepository.findById(2L)).thenReturn(Optional.empty()); // Account 2 not found
//    //     when(familyAccountRepository.findById(3L)).thenReturn(Optional.of(new FamilyAccount()));
//
//    //     assertThrows(FamilyAccountNotFoundException.class, () -> {
//    //         familyAccountService.terminateFamilyAccount(accountIds);
//    //     });
//
//    //     verify(familyAccountRepository, times(1)).findById(1L);
//    //     verify(familyAccountRepository, times(1)).findById(2L);
//    //     verify(familyAccountRepository, times(1)).findById(3L);
//    //     verify(familyAccountRepository, times(2)).save(any(FamilyAccount.class)); // Only one save call for Account 1
//    //                                                                               // and Account 3
//    // }
//}

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.usermanagement.client.BillingServiceCommunication;
import com.usermanagement.entity.FamilyAccount;
import com.usermanagement.entity.User;
import com.usermanagement.exception.AlreadyMemberOfAnotherFamilyAccountException;
import com.usermanagement.exception.FamilyAccountAlreadyExistsException;
import com.usermanagement.exception.UserNotFoundException;
import com.usermanagement.repository.FamilyAccountRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.FamilyAccountService;
import com.usermanagement.service.OtpService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class FamilyAccountServiceUnitTest {

    @Mock
    private FamilyAccountRepository familyAccountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OtpService otpService;

    @Mock
    private BillingServiceCommunication billingServiceCommunication;

    @InjectMocks
    private FamilyAccountService familyAccountService;

    @BeforeEach
    void setUp() {
        // Mock theUserRepository.findById() method to return a User object
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        
    }

    @Test
    void shouldCreateFamilyAccountSuccessfully() {
        // Act
        String message = familyAccountService.createFamilyAccount(1L);

        // Assert
        Assertions.assertEquals("Family Account Created Successfully", message);
    }

    @Test
    void shouldThrowUserNotFoundExceptionIfUserDoesNotExist() {
        // When
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(UserNotFoundException.class, () -> familyAccountService.createFamilyAccount(1L));
    }

    @Test
    void shouldThrowFamilyAccountAlreadyExistsExceptionIfUserAlreadyHasAFamilyAccount() {
        // When
        when(familyAccountRepository.findByPrimaryUser(any(User.class))).thenReturn(Optional.of(new FamilyAccount()));

        // Then
        Assertions.assertThrows(FamilyAccountAlreadyExistsException.class, () -> familyAccountService.createFamilyAccount(1L));
    }

    @Test
    void shouldThrowAlreadyMemberOfAnotherFamilyAccountExceptionIfUserIsAlreadyASecondaryUserInAnotherFamilyAccount() {
        // When
        User user = new User();
        user.setFamilyAccount(new FamilyAccount());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Then
        Assertions.assertThrows(AlreadyMemberOfAnotherFamilyAccountException.class, () -> familyAccountService.createFamilyAccount(1L));
    }
}

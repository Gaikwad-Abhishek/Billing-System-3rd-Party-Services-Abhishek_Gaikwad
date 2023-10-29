package com.usermanagement.dto;

import java.util.List;

import com.usermanagement.entity.User;
import com.usermanagement.enums.FamilyAccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyAccountDetailsDTO {
	
    private Long familyAccountId;
    private FamilyAccountStatus accountStatus;
    private UserDTO primaryUser;
    private List<UserSubscriptionDetailsDTO> secondaryUsers;

}

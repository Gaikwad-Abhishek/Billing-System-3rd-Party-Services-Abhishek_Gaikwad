package com.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
	
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String contactNo;

}
